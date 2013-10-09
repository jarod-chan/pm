package cn.fyg.pm.interfaces.web.module.trace.constructcert;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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
import cn.fyg.pm.application.facade.ConstructCertFacade;
import cn.fyg.pm.domain.model.construct.constructcert.CertItemOpinion;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertItem;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.OpinionItem;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.constructcert.query.CertQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.flow.FlowUtil;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/constructcert")
public class ConstructCertCtl {
	
	private static final String PATH="trace/constructcert/";
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
	@Autowired
	ConstructCertFacade constructCertFacade;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,CertQuery certQuery,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		certQuery.setProject(project);
		if(certQuery.getSupplier()!=null&&certQuery.getSupplier().getId()!=null){
			Supplier supplier=this.supplierService.find(certQuery.getSupplier().getId());
			certQuery.setSupplier(supplier);
		}
		List<ConstructCert> constructCertList = constructCertService.query(certQuery);
		List<ConstructCertDto> ConstructCertDtoList = constructCertAssembler.create(constructCertList);
		map.put("ConstructCertDtoList", ConstructCertDtoList);
		map.put("query", certQuery);
		return Page.LIST;
	}

	@RequestMapping(value="{constructCertId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("constructCertId") Long constructCertId,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		ConstructCert constructCert =constructCertId.longValue()>0?constructCertService.find(constructCertId):constructCertService.create(user,project,ConstructCertState.new_) ;
		map.put("constructCert", constructCert);
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
		
		List<ConstructCertItem> constructCertItemList =BindTool.changeEntityItems(ConstructCertItem.class, constructCert.getConstructCertItems(), constructCertItemsId);		
		constructCert.setConstructCertItems(constructCertItemList);
		BindTool.bindRequest(constructCert, request);
		
		constructCert=constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			Result result=constructCertFacade.commit(constructCert, user);
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
		Map<String, Opinion> checkerOpinion = flowUtil.getCheckerOpinion(opinions);
		map.put("checkerOpinion", checkerOpinion);
		Map<Long,OpinionItem> checkerOpinionItem=getOpinionItem(checkerOpinion.get("shr"),constructCert.getConstructCertItems().size());
		map.put("checkerOpinionItem", checkerOpinionItem);
		return Page.PRINT;
	}
	
	/**
	 * TODO 领域逻辑处理存在一定问题
	 * @param opinion
	 * @param size
	 * @return
	 */
	private Map<Long, OpinionItem> getOpinionItem(Opinion opinion, int size) {
		HashMap<Long,OpinionItem> map = new HashMap<Long,OpinionItem>();
		if(opinion==null||size==0) return map;
		
		for(int i=1;i<=size;i++){
			OpinionItem opinionItem = new OpinionItem();
			ResultEnum result = opinion.getResult();
			opinionItem.setResult(result);
			map.put(Long.valueOf(i), opinionItem);
		}
		
		if(opinion.getOpinionItems().size()==0) return map;
		
		for (OpinionItem opinionItem : opinion.getOpinionItems()) {
			map.put(opinionItem.getItemSn(), opinionItem);
		}
		
		return map;
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
		
		List<ConstructCertItem> constructCertItemList =BindTool.changeEntityItems(ConstructCertItem.class, constructCert.getConstructCertItems(), constructCertItemsId);		
		constructCert.setConstructCertItems(constructCertItemList);
		BindTool.bindRequest(constructCert, request);
		
		constructCert=constructCertService.save(constructCert);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			Result result =constructCertFacade.commitCheck(constructCert,user,taskId);
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
	

}
