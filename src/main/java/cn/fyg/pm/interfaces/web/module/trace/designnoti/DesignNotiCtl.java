package cn.fyg.pm.interfaces.web.module.trace.designnoti;

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
import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.design.TechType;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiItem;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.designnoti.flow.NotiVarname;
import cn.fyg.pm.interfaces.web.module.trace.designnoti.query.NotiQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/designnoti")
public class DesignNotiCtl {
	
	private static final String PATH="trace/designnoti/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
		String VIEW = PATH + "view";
		String CHECK = PATH + "check";
		String CHECK_EDIT = PATH + "check_edit";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	DesignNotiService designNotiService;
	@Autowired
	OpinionService opinionService;
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
	public String toList(@PathVariable("projectId")Long projectId,NotiQuery query,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		query.setProject(project);
		List<DesignNoti>  designNotiList= this.designNotiService.query(query);
		map.put("designNotiList", designNotiList);
		map.put("query", query);
		return Page.LIST;
	}
	
	@RequestMapping(value="{designNotiId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("designNotiId")Long designNotiId,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		DesignNoti designNoti = designNotiId.longValue()>0?this.designNotiService.find(designNotiId):this.designNotiService.create(user,project,DesignNotiState.new_);
		map.put("designNoti", designNoti);
		if(designNotiId.longValue()>0){
			HashMap<Long, List<Filestore>> fileMap = getNotiItemFile(designNoti);
			map.put("fileMap", fileMap);
		}
		map.put("techTypes", TechType.values());
		return Page.EDIT;
	}
	
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long designNotiId,@RequestParam("afteraction")String afteraction,@RequestParam(value="designNotiItemsId",required=false) Long[] designNotiItemsId,
			@RequestParam(value="itemfileSn",required=false)Long[] itemfileSn,@RequestParam(value="itemfileId",required=false)Long[] itemfileId,
			HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		
		DesignNoti designNoti =designNotiId!=null?this.designNotiService.find(designNotiId):this.designNotiService.create(user,project,DesignNotiState.saved);
		 
		List<DesignNotiItem> designNotiItemList = BindTool.changeEntityItems(DesignNotiItem.class,designNoti.getDesignNotiItems(),designNotiItemsId);
		designNoti.setDesignNotiItems(designNotiItemList);
		BindTool.bindRequest(designNoti,request);

		designNoti=this.designNotiService.save(designNoti);
		
		saveItemFile(itemfileSn,itemfileId,designNoti.getDesignNotiItems());
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		
		if(afteraction.equals("commit")){
			Result result=commit(designNoti, user);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/edit",designNoti.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:list";
			}
		}
		
		return "";
	}


	private void saveItemFile(Long[] itemfileSn, Long[] itemfileId,List<DesignNotiItem> designNotiItems) {
		if(itemfileSn==null&&itemfileId==null) return;
		Map<Long,ArrayList<Long>> itemFiles=new HashMap<Long,ArrayList<Long>>();
		for(int i=1,len=designNotiItems.size();i<=len;i++){
			itemFiles.put(Long.valueOf(i), new ArrayList<Long>());
		}
		for(int i=0,len=itemfileSn.length;i<len;i++){
			ArrayList<Long> arrayList = itemFiles.get(itemfileSn[i]);
			arrayList.add(itemfileId[i]);
		}
		for (DesignNotiItem designNotiItem : designNotiItems) {
			Long id=designNotiItem.getId();
			Long sn=designNotiItem.getSn();
			ArrayList<Long> fileIds = itemFiles.get(sn);
			if(!fileIds.isEmpty()){
				this.busifileService.associateFile(BusiCode.pm_designnoti_item, id, fileIds.toArray(new Long[fileIds.size()]));
			}
		}
	}

	@Transactional
	private Result commit(DesignNoti designNoti, User user) {
		Result result = this.designNotiService.verifyForCommit(designNoti);
		if(result.notPass()) return result;
		designNoti.setState(DesignNotiState.commit);
		designNoti=this.designNotiService.save(designNoti);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, designNoti.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(NotiVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@RequestMapping(value="{designNotiId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("designNotiId")Long designNotiId,Map<String,Object> map){
		DesignNoti designNoti =this.designNotiService.find(designNotiId);
		map.put("designNoti", designNoti);
		List<Opinion> opinions = this.opinionService.listOpinions(DesignNoti.BUSI_CODE, designNotiId);
		map.put("opinions", opinions);
		
		HashMap<Long, List<Filestore>> fileMap = getNotiItemFile(designNoti);
		map.put("fileMap", fileMap);
		
		return Page.VIEW;
	}

	private HashMap<Long, List<Filestore>> getNotiItemFile(DesignNoti designNoti) {
		BusiCode busiCode=BusiCode.pm_designnoti_item;
		HashMap<Long, List<Filestore>> fileMap = new HashMap<Long,List<Filestore>>();
		for (DesignNotiItem designNotiItem : designNoti.getDesignNotiItems()) {
			List<Filestore> filestores = this.busifileService.findFilestores(busiCode, designNotiItem.getId());
			fileMap.put(designNotiItem.getSn(), filestores);
		}
		return fileMap;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("designNotiId") Long designNotiId,RedirectAttributes redirectAttributes){
		deleteDesignNoti(designNotiId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功！"));
		return "redirect:list";
	}

	@Transactional
	public void deleteDesignNoti(Long designNotiId) {
		DesignNoti designNoti =this.designNotiService.find(designNotiId);
		for (DesignNotiItem designNotiItem : designNoti.getDesignNotiItems()) {
			this.busifileService.removeAssociatedFile(BusiCode.pm_designnoti_item, designNotiItem.getId());
		}
 		this.designNotiService.delete(designNotiId);
	}
	
	@RequestMapping(value="{designNotiId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable("projectId")Long projectId,@PathVariable("designNotiId")Long designNotiId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		DesignNoti designNoti =this.designNotiService.find(designNotiId);
		map.put("designNoti", designNoti);
		HashMap<Long, List<Filestore>> fileMap = getNotiItemFile(designNoti);
		map.put("fileMap", fileMap);

		List<Opinion> opinions = this.opinionService.listOpinions(DesignNoti.BUSI_CODE, designNotiId);
		map.put("opinions", opinions);
		
		map.put("resultList", ResultEnum.agreeItems());
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		map.put("busiCode", DesignNoti.BUSI_CODE);
		
		return Page.CHECK;
	}
	
	@RequestMapping(value="{designNotiId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("projectId")Long projectId,@PathVariable("designNotiId")Long designNotiId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		DesignNoti designNoti = this.designNotiService.find(designNotiId);
		map.put("designNoti", designNoti);
		if(designNotiId.longValue()>0){
			HashMap<Long, List<Filestore>> fileMap = getNotiItemFile(designNoti);
			map.put("fileMap", fileMap);
		}
		map.put("techTypes", TechType.values());
		map.put("taskId", taskId);
		List<Opinion> opinions = this.opinionService.listOpinions(DesignNoti.BUSI_CODE, designNotiId);
		map.put("opinions", opinions);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long designNotiId,@RequestParam("afteraction")String afteraction,@RequestParam(value="designNotiItemsId",required=false) Long[] designNotiItemsId,
			@RequestParam(value="itemfileSn",required=false)Long[] itemfileSn,@RequestParam(value="itemfileId",required=false)Long[] itemfileId,
			@RequestParam(value="taskId",required=false)String taskId,
			HttpServletRequest request,RedirectAttributes redirectAttributes){
		
		DesignNoti designNoti =this.designNotiService.find(designNotiId);
		 
		List<DesignNotiItem> designNotiItemList = BindTool.changeEntityItems(DesignNotiItem.class,designNoti.getDesignNotiItems(),designNotiItemsId);
		designNoti.setDesignNotiItems(designNotiItemList);
		BindTool.bindRequest(designNoti,request);

		designNoti=this.designNotiService.save(designNoti);
		
		saveItemFile(itemfileSn,itemfileId,designNoti.getDesignNotiItems());
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			Result result =commitCheck(designNoti,user,taskId);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:../%s/checkedit?taskId=%s",designNoti.getId(),taskId);
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:/task/list";
			}
		}
		
		
		if(afteraction.equals("invalid")){
			User user = sessionUtil.getValue("user");
			designNoti.setState(DesignNotiState.invalid);
			this.designNotiService.save(designNoti);
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(NotiVarname.STATE, designNoti.getState());
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

	private Result commitCheck(DesignNoti designNoti, User user, String taskId) {
		Result result = this.designNotiService.verifyForCommit(designNoti);
		if(result.notPass()) return result;
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put(NotiVarname.STATE, designNoti.getState());
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId,variableMap);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}

	

}
