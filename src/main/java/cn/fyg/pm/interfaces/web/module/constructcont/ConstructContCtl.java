package cn.fyg.pm.interfaces.web.module.constructcont;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.model.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("constructcont")
public class ConstructContCtl {
	
	private static final String PATH="constructcont/";
	private interface Page {
		String LIST = PATH + "list";
		String NEW = PATH + "new";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK = PATH + "check";
	}
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@Autowired
	ContractService contractService;
	@Autowired
	ConstructContService constructContService;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<ConstructCont> constructContList = constructContService.findAll();
		map.put("constructContList", constructContList);
		return Page.LIST;
	}

	@RequestMapping(value="new",method=RequestMethod.GET)
	public String toNew(Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		map.put("project", project);
		List<Contract> contractList = contractService.findByProject(project);
		map.put("contractList", contractList);
		ConstructCont constructCont = constructContService.create(user);
		map.put("constructCont", constructCont);
		return Page.NEW;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(ConstructCont constructCont,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes){
		User user = sessionUtil.getValue("user");
		constructCont.setState(ConstructContState.saved);
		constructCont.setCreater(user);
		constructCont.setCreatedate(new Date());
		constructContService.save(constructCont);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			constructCont.setState(ConstructContState.commit);
			constructCont=constructContService.save(constructCont);
			commit(constructCont, user);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:list";
		}
		
		return "";
	}

	@RequestMapping(value="{constructContId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("constructContId")Long constructContId,Map<String,Object> map){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		List<Contract> contractList = contractService.findByProject(constructCont.getConstructKey().getProject());
		map.put("contractList", contractList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@RequestParam("id")Long id,@RequestParam("afteraction")String afteraction,@RequestParam("constructContItemsId") Long[] constructContItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		ConstructCont constructCont = constructContService.find(id);
		Map<Long,ConstructContItem> constructContItemMap=getConstructContItemMap(constructCont.getConstructContItems());
		
		List<ConstructContItem> ConstructContItemList = new ArrayList<ConstructContItem>();
		for (int i = 0,len=constructContItemsId.length; i < len; i++) {
			ConstructContItem constructContItem = constructContItemsId[i]>0?constructContItemMap.get(constructContItemsId[i]):new ConstructContItem();
			ConstructContItemList.add(constructContItem);
		}
		constructCont.setConstructContItems(ConstructContItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCont);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		constructCont=constructContService.save(constructCont);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			constructCont.setState(ConstructContState.commit);
			constructCont=constructContService.save(constructCont);
			User user = sessionUtil.getValue("user");
			commit(constructCont, user);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:list";
		}
		
		return "";
	}

	/**
	 * 提交联系单
	 * @param constructCont
	 * @param user
	 */
	private void commit(ConstructCont constructCont, User user) {
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, constructCont.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(Varname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
	

	private Map<Long, ConstructContItem> getConstructContItemMap(List<ConstructContItem> constructContItems) {
		Map<Long,ConstructContItem> map = new HashMap<Long,ConstructContItem>();
		for (ConstructContItem constructContItem : constructContItems) {
			map.put(constructContItem.getId(), constructContItem);
		}
		return map;
	}
	
	@RequestMapping(value="commit",method=RequestMethod.POST)
	public String commit(@RequestParam("constructContId") Long constructContId,RedirectAttributes redirectAttributes){
		User user = sessionUtil.getValue("user");
		String userKey=user.getKey();
		ConstructCont constructCont = constructContService.find(constructContId);
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, constructCont.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(Varname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("施工联系单：%s已经提交流程！",constructCont.getNo()));
		return "redirect:list";
	}

	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructContId") Long constructContId){
		constructContService.delete(constructContId);
		return "redirect:list";
	}
	
	@RequestMapping(value="{constructContId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("constructContId")Long constructContId,Map<String,Object> map){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{constructContId}/items",method=RequestMethod.GET)
	@ResponseBody 
	public List<ConstructContItem> loadConstructContItemList(@PathVariable("constructContId")Long constructContId){
		ConstructCont constructCont = constructContService.find(constructContId);
		return constructCont.getConstructContItems();
	}
	
	@RequestMapping(value="{businessId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable(value="businessId")Long businessId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		return Page.CHECK;
	}
	
}