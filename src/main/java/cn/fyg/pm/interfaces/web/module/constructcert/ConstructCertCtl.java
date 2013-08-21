package cn.fyg.pm.interfaces.web.module.constructcert;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.construct.constructcert.CertItemOpinion;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertItem;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.constructcert.flow.CertVarname;
import cn.fyg.pm.interfaces.web.module.constructcert.query.CertQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.flow.FlowUtil;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/constructcert")
public class ConstructCertCtl {
	
	private static final String PATH="constructcert/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String PRINT =PATH + "print";
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
	@Autowired
	UserService userService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	@Qualifier("constructcert")
	FlowUtil flowUtil;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,CertQuery certQuery,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		certQuery.setProject(project);
		List<ConstructCert> constructCertList = constructCertService.query(certQuery);
		List<ConstructCertDto> ConstructCertDtoList = constructCertAssembler.create(constructCertList);
		map.put("ConstructCertDtoList", ConstructCertDtoList);
		map.put("query", certQuery);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.contra,Supptype.construct));
		return Page.LIST;
	}

	@RequestMapping(value="{constructCertId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		ConstructCert constructCert =constructCertId.longValue()>0?constructCertService.find(constructCertId):constructCertService.create(user,project,ConstructCertState.new_) ;
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.constructContCanBeSelected(project,ConstructContState.finish,constructCertId);
		map.put("constructContList", constructContList);
		ConstructCont constructCont=constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("certItemOpinionList", CertItemOpinion.values());
		return Page.EDIT;
	}

	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long constructCertId,@RequestParam(value="constructCertItemsId",required=false) Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		ConstructCert constructCert = constructCertId!=null?constructCertService.find(constructCertId):constructCertService.create(user, project,ConstructCertState.saved);
		
		Map<Long,ConstructCertItem> constructCertMap=getConstructCertMap(constructCert.getConstructCertItems());
		
		List<ConstructCertItem> constructCertItemList = new ArrayList<ConstructCertItem>();
		for(int i=0,len=constructCertItemsId==null?0:constructCertItemsId.length;i<len;i++){
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
			Result result=commit(constructCert, user);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/edit",constructCert.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:list";
			}
		}
		
		return "";
	
	}
	
	@Transactional
	private Result commit(ConstructCert constructCert, User user) {
		Result result = this.constructCertService.verifyForCommit(constructCert);
		if(result.notPass()) return result;
		constructCert.setState(ConstructCertState.commit);
		constructCert=constructCertService.save(constructCert);
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
		return result;
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
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("constructCertId")Long constructCertId,Map<String,Object> map){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("constructCert", constructCert);
		List<Opinion> opinions = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("opinions", opinions);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{constructCertId}/print",method=RequestMethod.GET)
	public String toPrint(@PathVariable("projectId")Long projectId,@PathVariable("constructCertId")Long constructCertId,Map<String,Object> map){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("constructCert", constructCert);
		List<Opinion> opinions = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("flowChecker", flowUtil.getFlowChecker(opinions));
		return Page.PRINT;
	}
	
	@RequestMapping(value="{constructCertId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable("projectId")Long projectId,@PathVariable(value="constructCertId")Long constructCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		List<Opinion> opinions = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("opinions", opinions);
		map.put("resultList", ResultEnum.agreeItems());
		map.put("busiCode", ConstructCert.BUSI_CODE);
		return Page.CHECK;
	}
	
	
	
	
	
	
	@RequestMapping(value="{constructCertId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		ConstructCert constructCert = constructCertService.find(constructCertId);
		map.put("constructCert", constructCert);
		List<ConstructCont> constructContList = constructContService.constructContCanBeSelected(constructCert.getConstructKey().getProject(),ConstructContState.finish,constructCertId);
		map.put("constructContList", constructContList);
		ConstructCont constructCont=constructContService.findByConstructKey(constructCert.getConstructKey());
		map.put("constructCont", constructCont);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(ConstructCert.BUSI_CODE, constructCertId);
		map.put("opinionList", opinionList);
		map.put("certItemOpinionList", CertItemOpinion.values());
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam(value="constructCertItemsId",required=false) Long[] constructCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
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
			Result result =commitCheck(constructCert,user,taskId);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/checkedit",constructCert.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:/task/list";
			}	
		}
		
		return "";
	}
	
	@Transactional
	private Result commitCheck(ConstructCert constructCert,User user,String taskId){
		Result result = this.constructCertService.verifyForCommit(constructCert);
		if(result.notPass()) return result;
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}

}
