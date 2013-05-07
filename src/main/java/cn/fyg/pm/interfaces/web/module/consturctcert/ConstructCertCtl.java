package cn.fyg.pm.interfaces.web.module.consturctcert;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.constructcert.ConstructCertItem;
import cn.fyg.pm.domain.model.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.interfaces.web.module.constructcont.flow.ContVarname;
import cn.fyg.pm.interfaces.web.module.consturctcert.flow.CertVarname;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("constructcert")
public class ConstructCertCtl {
	
	private static final String PATH="constructcert/";
	private interface Page {
		String LIST = PATH + "list";
		String NEW = PATH + "new";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK = PATH + "check";
		String CHECK_EDIT = PATH + "check_edit";
	}
	
	@Autowired
	ConstructContService constructContService;
	@Autowired
	ConstructCertService constructCertService;
	@Autowired
	ConstructCertAssembler constructCertAssembler;
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
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<ConstructCert> constructCertList = constructCertService.findAll();
		List<ConstructCertDto> ConstructCertDtoList = constructCertAssembler.create(constructCertList);
		map.put("ConstructCertDtoList", ConstructCertDtoList);
		return Page.LIST;
	}
	
	@RequestMapping(value="new",method=RequestMethod.GET)
	public String toNew(Map<String,Object> map){
		User user = sessionUtil.getValue("user");
		Project project = sessionUtil.getValue("project");
		map.put("project", project);
		List<ConstructCont> constructContList = constructContService.findByProject(project);
		map.put("constructContList", constructContList);
		ConstructCert constructCert = constructCertService.create(user);
		map.put("constructCert", constructCert);
		return Page.NEW;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(ConstructCert constructCert,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes){
		User user = sessionUtil.getValue("user");
		constructCert.setState(ConstructCertState.saved);
		constructCert.setCreater(user);
		constructCert.setCreatedate(new Date());
		constructCert = constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			constructCert.setState(ConstructCertState.commit);
			constructCert=constructCertService.save(constructCert);
			commit(constructCert,user);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:list";
		}
		
		return "";
	}
	
	private void commit(ConstructCert constructCert, User user) {
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, constructCert.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(CertVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

	@RequestMapping(value="{constructCertId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.findByProject(constructCert.getConstructKey().getProject());
		map.put("constructContList", constructContList);
		return Page.EDIT;
	}

	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@RequestParam("id")Long id,@RequestParam("constructCertItemsId") Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes){
		ConstructCert constructCert = constructCertService.find(id);
		Map<Long,ConstructCertItem> constructCertMap=getConstructCertMap(constructCert.getConstructCertItems());
		
		List<ConstructCertItem> constructCertItemList = new ArrayList<ConstructCertItem>();
		for(int i=0,len=constructCertItemsId.length;i<len;i++){
			ConstructCertItem constructCertItem=(constructCertItemsId[i]>0?constructCertMap.get(constructCertItemsId[i]):new ConstructCertItem());
			constructCertItemList.add(constructCertItem);
		}
		constructCert.setConstructCertItems(constructCertItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCert);
		binder.bind(request);
		constructCert=constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			constructCert.setState(ConstructCertState.commit);
			constructCert=constructCertService.save(constructCert);
			User user = sessionUtil.getValue("user");
			commit(constructCert, user);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:list";
		}
		
		return "";

	}
	

	private Map<Long, ConstructCertItem> getConstructCertMap(List<ConstructCertItem> constructCertItemList) {
		HashMap<Long, ConstructCertItem> constructCertMap = new HashMap<Long,ConstructCertItem>();
		for (ConstructCertItem constructCertItem : constructCertItemList) {
			constructCertMap.put(constructCertItem.getId(), constructCertItem);
		}
		return constructCertMap;
	}

	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("constructCertId") Long constructCertId){
		constructCertService.delete(constructCertId);
		return "redirect:list";
	}
	
	@RequestMapping(value="{constructCertId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("constructCertId")Long constructCertId,Map<String,Object> map){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("constructCert", constructCert);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{constructCertId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable(value="constructCertId")Long constructCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
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
		opinion.setBusiCode(ConstructCert.BUSI_CODE);
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
	
	@RequestMapping(value="{constructCertId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.findByProject(constructCert.getConstructKey().getProject());
		map.put("constructContList", constructContList);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("opinionList", opinionList);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam("constructCertItemsId") Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(id);
		Map<Long,ConstructCertItem> constructCertMap=getConstructCertMap(constructCert.getConstructCertItems());
		
		List<ConstructCertItem> constructCertItemList = new ArrayList<ConstructCertItem>();
		for(int i=0,len=constructCertItemsId.length;i<len;i++){
			ConstructCertItem constructCertItem=(constructCertItemsId[i]>0?constructCertMap.get(constructCertItemsId[i]):new ConstructCertItem());
			constructCertItemList.add(constructCertItem);
		}
		constructCert.setConstructCertItems(constructCertItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(constructCert);
		binder.bind(request);
		constructCert=constructCertService.save(constructCert);
		
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
