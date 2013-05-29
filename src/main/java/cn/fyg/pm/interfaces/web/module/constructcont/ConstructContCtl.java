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
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
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

import cn.fyg.pm.application.service.ConstructContService;
import cn.fyg.pm.application.service.ContractService;
import cn.fyg.pm.application.service.OpinionService;
import cn.fyg.pm.application.service.SupplierService;
import cn.fyg.pm.application.service.UserService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.ContractType;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.interfaces.web.module.constructcont.flow.ContVarname;
import cn.fyg.pm.interfaces.web.module.constructcont.query.ContQuery;
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
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK = PATH + "check";
		String CHECK_EDIT = PATH + "check_edit";
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
	@Autowired
	TaskService taskService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	UserService userService;
	@Autowired
	ContractService ContractService;
	@Autowired
	SupplierService supplierService;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(ContQuery query,Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		query.setProject(project);
		List<ConstructCont> constructContList = constructContService.query(query);
		map.put("constructContList", constructContList);
		map.put("userList", userService.findAll());
		map.put("stateList", ContQuery.State.values());
		map.put("contractSpecList", ContractSpec.values());
		map.put("query", query);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.contra,Supptype.construct));
		return Page.LIST;
	}

	@RequestMapping(value="{constructContId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("constructContId")Long constructContId,Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		ConstructCont constructCont = constructContId.longValue()>0?constructContService.find(constructContId):constructContService.create(user,project,ConstructContState.new_,false);
		map.put("constructCont", constructCont);
		List<Contract> contractList = contractService.findByProjectAndType(constructCont.getConstructKey().getProject(),ContractType.construct);
		map.put("contractList", contractList);
		map.put("userList", userService.findAll());
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@RequestParam("id")Long constructContId,@RequestParam("afteraction")String afteraction,@RequestParam(value="constructContItemsId",required=false) Long[] constructContItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		
		ConstructCont constructCont =constructContId!=null?constructContService.find(constructContId):constructContService.create(user,project,ConstructContState.saved,true);
		 
		Map<Long,ConstructContItem> constructContItemMap=getConstructContItemMap(constructCont.getConstructContItems());	
		List<ConstructContItem> ConstructContItemList = new ArrayList<ConstructContItem>();
		for (int i = 0,len=constructContItemsId==null?0:constructContItemsId.length; i < len; i++) {
			ConstructContItem constructContItem = constructContItemsId[i]>0?constructContItemMap.get(constructContItemsId[i]):new ConstructContItem();
			ConstructContItemList.add(constructContItem);
		}
		constructCont.setConstructContItems(ConstructContItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCont);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		//TODO:constructKey 补充填入供应商字段
		ConstructKey constructKey = constructCont.getConstructKey();
		if(constructKey.getContract()!=null){
			Contract contract = contractService.find(constructKey.getContract().getId());
			constructKey.setSupplier(contract.getSupplier());
		}
		constructCont=constructContService.save(constructCont);
		
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
			runtimeService.startProcessInstanceByKey(ContVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
	

	private Map<Long, ConstructContItem> getConstructContItemMap(List<ConstructContItem> constructContItems) {
		Map<Long,ConstructContItem> map = new HashMap<Long,ConstructContItem>();
		if(constructContItems==null) return map;
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
			runtimeService.startProcessInstanceByKey(ContVarname.PROCESS_DEFINITION_KEY, variableMap);			
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
	public String toView(@PathVariable("constructContId")Long constructContId,Map<String,Object> map,@RequestParam(value="notback",required=false)Boolean notback){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		map.put("notback", notback);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{constructContId}/items",method=RequestMethod.GET)
	@ResponseBody 
	public List<ConstructContItem> loadConstructContItemList(@PathVariable("constructContId")Long constructContId){
		ConstructCont constructCont = constructContService.find(constructContId);
		return constructCont.getConstructContItems();
	}
	
	@RequestMapping(value="{constructContId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable(value="constructContId")Long constructContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		map.put("resultList", ResultEnum.agreeItems());
		return Page.CHECK;
	}
	
	@RequestMapping(value="check/commit",method=RequestMethod.POST)
	public String checkCommit(Opinion opinion,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		User user = sessionUtil.getValue("user");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		opinion.setBusiCode(ConstructCont.BUSI_CODE);
		opinion.setTaskKey(task.getTaskDefinitionKey());
		opinion.setTaskName(task.getName());
		opinion.setUserKey(user.getKey());
		opinion.setUserName(user.getName());
		opinionService.append(opinion);
		runtimeService.setVariableLocal(task.getExecutionId(), ContVarname.OPINION,opinion.getResult().val());
		taskService.complete(task.getId());
		redirectAttributes
			.addFlashAttribute(AppConstant.MESSAGE_NAME,info("任务完成"));
		return "redirect:/task/list";
	}
	
	@RequestMapping(value="{constructContId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("constructContId")Long constructContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCont constructCont = constructContService.find(constructContId);
		map.put("constructCont", constructCont);
		List<Contract> contractList = contractService.findByProject(constructCont.getConstructKey().getProject());
		map.put("contractList", contractList);
		map.put("userList", userService.findAll());
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(ConstructCont.BUSI_CODE, constructContId);
		map.put("opinionList", opinionList);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam("afteraction")String afteraction,@RequestParam("constructContItemsId") Long[] constructContItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
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
			return "redirect:/task/list";
		}
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			try{
				identityService.setAuthenticatedUserId(user.getKey());
				taskService.complete(taskId);
			} finally {
				identityService.setAuthenticatedUserId(null);
			}
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:/task/list";
		}
		
		return "";
	}
	
}