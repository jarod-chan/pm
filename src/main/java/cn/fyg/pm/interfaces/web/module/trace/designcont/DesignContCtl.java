package cn.fyg.pm.interfaces.web.module.trace.designcont;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.BusifileService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.design.TechType;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContItem;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.design.designcont.reason.Reason;
import cn.fyg.pm.domain.model.design.designcont.reason.ReasonItem;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.designcont.flow.ContVarname;
import cn.fyg.pm.interfaces.web.module.trace.designcont.query.ContQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/designcont")
public class DesignContCtl {
	
	private static final String PATH="trace/designcont/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK = PATH + "check";
		String CHECK_EDIT = PATH + "check_edit";
		String CONFIRM = PATH +"confirm";
		String SENDLOG = PATH +"sendlog";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	DesignContService designContService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	DesignNotiService designNotiService;
	@Autowired
	ContractService contractService;
	@Autowired
	BusifileService busifileService;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,ContQuery query,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		query.setProject(project);
		List<DesignCont>  designContList= this.designContService.query(query);
		map.put("designContList", designContList);
		map.put("query", query);
		return Page.LIST;
	}
	
	@RequestMapping(value="{designContId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		DesignCont designCont = designContId.longValue()>0?this.designContService.find(designContId):this.designContService.create(user,project,DesignContState.new_);
		map.put("designCont", designCont);
		List<DesignNoti> designNotiList = this.designNotiService.findByProject(project,DesignNotiState.finish);
		map.put("designNotiList", designNotiList);
		List<Contract> contractList = this.contractService.findByProjectAndType(project, ContractType.design);
		map.put("contractList", contractList);
		List<ReasonItem> reasonItems = Reason.getReasonItemList();
		map.put("reasonItems", reasonItems);
		if(designCont.getId()!=null){
			BusiCode busiCode=DesignCont.BUSI_CODE;
			Long busiId=designCont.getId();
			map.put("filestores", this.busifileService.findFilestores(busiCode, busiId));
		}
		map.put("techTypes", TechType.values());
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long designContId,@RequestParam("afteraction")String afteraction,@RequestParam(value="designContItemsId",required=false) Long[] designContItemsId,@RequestParam(value="filestore_id",required=false)Long[] filestore_id,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		
		DesignCont designCont =designContId!=null?this.designContService.find(designContId):this.designContService.create(user,project,DesignContState.saved);
		 
		List<DesignContItem> designContItemList = BindTool.changeEntityItems(DesignContItem.class,designCont.getDesignContItems(),designContItemsId);
		designCont.setDesignContItems(designContItemList);
		BindTool.bindRequest(designCont,request);

		designCont=this.designContService.save(designCont);
		

		BusiCode busiCode=DesignCont.BUSI_CODE;
		Long busiId=designCont.getId();
		this.busifileService.associateFile(busiCode, busiId, filestore_id);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		
		if(afteraction.equals("commit")){
			Result result=commit(designCont, user);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/edit",designCont.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:list";
			}
		}
		
		return "";
	}

	@Transactional
	private Result commit(DesignCont designCont, User user) {
		Result result = this.designContService.verifyForCommit(designCont);
		if(result.notPass()) return result;
		designCont.setState(DesignContState.commit);
		designCont=this.designContService.save(designCont);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, designCont.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(ContVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@RequestMapping(value="{designContId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map){
		DesignCont designCont = this.designContService.find(designContId);
		map.put("designCont", designCont);
		map.put("filestores", this.busifileService.findFilestores(DesignCont.BUSI_CODE, designContId));
		
		List<Opinion> opinions = this.opinionService.listOpinions(DesignCont.BUSI_CODE, designContId);
		map.put("opinions", opinions);
		
		return Page.VIEW;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("designContId") Long designContId,RedirectAttributes redirectAttributes){
		this.busifileService.removeAssociatedFile(DesignCont.BUSI_CODE, designContId);
		this.designContService.delete(designContId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功！"));
		return "redirect:list";
	}
	
	@RequestMapping(value="{designContId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		DesignCont designCont = this.designContService.find(designContId);
		map.put("designCont", designCont);
		map.put("filestores", this.busifileService.findFilestores(DesignCont.BUSI_CODE, designContId));

		List<Opinion> opinions = this.opinionService.listOpinions(DesignCont.BUSI_CODE, designContId);
		map.put("opinions", opinions);
		
		map.put("resultList", ResultEnum.agreeItems());
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		map.put("busiCode", DesignCont.BUSI_CODE);
		
		return Page.CHECK;
	}
	
	@RequestMapping(value="{designContId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		Project project = new Project();
		project.setId(projectId);
		
		DesignCont designCont =this.designContService.find(designContId);
		map.put("designCont", designCont);
		if(designCont.getId()!=null){
			BusiCode busiCode=DesignCont.BUSI_CODE;
			Long busiId=designCont.getId();
			map.put("filestores", this.busifileService.findFilestores(busiCode, busiId));
		}
		
		List<DesignNoti> designNotiList = this.designNotiService.findByProject(project,DesignNotiState.finish);
		map.put("designNotiList", designNotiList);
		List<Contract> contractList = this.contractService.findByProjectAndType(project, ContractType.design);
		map.put("contractList", contractList);
		List<ReasonItem> reasonItems = Reason.getReasonItemList();
		map.put("reasonItems", reasonItems);
		map.put("techTypes", TechType.values());
		
		map.put("taskId", taskId);
		List<Opinion> opinions = this.opinionService.listOpinions(DesignCont.BUSI_CODE, designContId);
		map.put("opinions", opinions);
		
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long designContId,@RequestParam("afteraction")String afteraction,
			@RequestParam(value="designContItemsId",required=false) Long[] designContItemsId,@RequestParam(value="filestore_id",required=false)Long[] filestore_id,
			@RequestParam(value="taskId",required=false)String taskId,
			HttpServletRequest request,RedirectAttributes redirectAttributes){
		
		DesignCont designCont =this.designContService.find(designContId);
		 
		List<DesignContItem> designContItemList = BindTool.changeEntityItems(DesignContItem.class,designCont.getDesignContItems(),designContItemsId);
		designCont.setDesignContItems(designContItemList);
		BindTool.bindRequest(designCont,request);

		designCont=this.designContService.save(designCont);
		
		BusiCode busiCode=DesignCont.BUSI_CODE;
		Long busiId=designCont.getId();
		this.busifileService.associateFile(busiCode, busiId, filestore_id);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			Result result=commitCheck(designCont, user,taskId);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:../%s/checkedit?taskId=%s",designCont.getId(),taskId);
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:/task/list";
			}
		}
		
		if(afteraction.equals("invalid")){
			User user = sessionUtil.getValue("user");
			designCont.setState(DesignContState.invalid);
			this.designContService.save(designCont);
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(ContVarname.STATE, designCont.getState());
			try{
				identityService.setAuthenticatedUserId(user.getKey());
				taskService.complete(taskId,variableMap);
			} finally {
				identityService.setAuthenticatedUserId(null);
			}
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("单据已经作废！"));
			return "redirect:/task/list";
		}
		
		return "";
	}

	private Result commitCheck(DesignCont designCont, User user,String taskId) {
		Result result = this.designContService.verifyForCommit(designCont);
		if(result.notPass()) return result;
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put(ContVarname.STATE, designCont.getState());
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId,variableMap);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@RequestMapping(value="{designContId}/confirm",method=RequestMethod.GET)
	public String toConfirm(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		DesignCont designCont = this.designContService.find(designContId);
		map.put("designCont", designCont);
		map.put("filestores", this.busifileService.findFilestores(DesignCont.BUSI_CODE, designContId));

		List<Opinion> opinions = this.opinionService.listOpinions(DesignCont.BUSI_CODE, designContId);
		map.put("opinions", opinions);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		map.put("busiCode", DesignCont.BUSI_CODE);
		
		return Page.CONFIRM;
	}
	
	//TODO 任务节点执行逻辑基本相同，应该屏蔽activiti层，执行自己的任务节点操作activitie的引擎，记录下自己的节点信息
	@RequestMapping(value="confirm",method=RequestMethod.POST)
	public String doConfirm(@PathVariable("projectId")Long projectId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId,RedirectAttributes redirectAttributes){
		User user = sessionUtil.getValue("user");
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("任务完成！"));
		return "redirect:/task/list";
	}
	
	@RequestMapping(value="{designContId}/sendlog",method=RequestMethod.GET)
	public String toSendLog(@PathVariable("projectId")Long projectId,@PathVariable("designContId")Long designContId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		DesignCont designCont = this.designContService.find(designContId);
		map.put("designCont", designCont);
		map.put("filestores", this.busifileService.findFilestores(DesignCont.BUSI_CODE, designContId));

		List<Opinion> opinions = this.opinionService.listOpinions(DesignCont.BUSI_CODE, designContId);
		map.put("opinions", opinions);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		map.put("busiCode", DesignCont.BUSI_CODE);
		
		return Page.SENDLOG;
	}
	
	@RequestMapping(value="sendlog",method=RequestMethod.POST)
	public String doSendLog(@PathVariable("projectId")Long projectId,@RequestParam("id")Long designContId,SendLogBean sendLogBean,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId,RedirectAttributes redirectAttributes){
		User user = sessionUtil.getValue("user");
		this.designContService.sendLog(designContId, sendLogBean.getReceiver(), sendLogBean.getSendnumb());
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("任务完成！"));
		return "redirect:/task/list";
	}

}
