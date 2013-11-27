package cn.fyg.pm.interfaces.web.module.trace.purchasereq;

import static cn.fyg.pm.interfaces.web.shared.message.Message.error;
import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.application.facade.PurchaseReqFacade;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.purchasereq.query.ReqQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/purchasereq")
public class PurchaseReqCtl {
	
	private static final String PATH="trace/purchasereq/";
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
	PurchaseReqService purchaseReqService;
	@Autowired
	ContractService contractService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	TaskService taskService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	ReqItemFacade reqItemFacade;
	@Autowired
	PurchaseReqFacade purchaseReqFacade;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,ReqQuery query,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		query.setProject(project);
		List<PurchaseReq>  purchaseReqList= this.purchaseReqService.findAll(query.getSpec(), query.getSort());
		map.put("purchaseReqList", purchaseReqList);
		map.put("query", query);
		return Page.LIST;
	}
	
	@RequestMapping(value="{purchaseReqId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("purchaseReqId")Long purchaseReqId,Map<String,Object> map){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		PurchaseReq purchaseReq = purchaseReqId.longValue()>0?purchaseReqService.find(purchaseReqId):purchaseReqService.create(user,project,PurchaseReqState.new_);
		map.put("purchaseReq", purchaseReq);
		map.put("maxPurchaseReqDay", 30);
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@PathVariable("projectId")Long projectId,@RequestParam("id")Long purchaseReqId,@RequestParam("afteraction")String afteraction,@RequestParam(value="purchaseReqItemsId",required=false) Long[] purchaseReqItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = new Project();
		project.setId(projectId);
		User user = sessionUtil.getValue("user");
		
		PurchaseReq purchaseReq =purchaseReqId!=null?purchaseReqService.find(purchaseReqId):purchaseReqService.create(user,project,PurchaseReqState.saved);
		 
		List<PurchaseReqItem> purchaseReqItemList=BindTool.changeEntityItems(PurchaseReqItem.class, purchaseReq.getPurchaseReqItems(), purchaseReqItemsId);
		purchaseReq.setPurchaseReqItems(purchaseReqItemList);
		BindTool.bindRequest(purchaseReq, request);
		
		purchaseReq=purchaseReqService.save(purchaseReq);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			Result result=purchaseReqFacade.commit(purchaseReq, user);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/edit",purchaseReq.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:list";
			}
		}
		
		return "";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("purchaseReqId") Long purchaseReqId,RedirectAttributes redirectAttributes){
		purchaseReqService.delete(purchaseReqId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功！"));
		return "redirect:list";
	}

	@RequestMapping(value="{purchaseReqId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("projectId")Long projectId,@PathVariable("purchaseReqId")Long purchaseReqId,Map<String,Object> map){
		PurchaseReq purchaseReq = purchaseReqService.find(purchaseReqId);
		map.put("purchaseReq", purchaseReq);
		List<Opinion> opinions = opinionService.listOpinions(PurchaseReq.BUSI_CODE, purchaseReqId);
		map.put("opinions", opinions);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{purchaseReqId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable("projectId")Long projectId,@PathVariable(value="purchaseReqId")Long purchaseReqId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseReq purchaseReq = purchaseReqService.find(purchaseReqId);
		map.put("purchaseReq", purchaseReq);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		List<Opinion> opinions = opinionService.listOpinions(PurchaseReq.BUSI_CODE, purchaseReqId);
		map.put("opinions", opinions);
		map.put("resultList", ResultEnum.agreeItems());
		map.put("busiCode", PurchaseReq.BUSI_CODE);
		return Page.CHECK;
	}
	
	
	/**
	 * 供外部接口调用
	 */
	@RequestMapping(value="{purchaseKeyId}/items/{uptype}/{upid}",method=RequestMethod.GET)
	@ResponseBody 
	public List<ReqItemDto> getReqItemList(@PathVariable("purchaseKeyId")Long purchaseKeyId,@PathVariable("uptype")UptypeEnum uptype,@PathVariable("upid")Long upid){
		return reqItemFacade.getReqItemList(purchaseKeyId, uptype, upid);
	}

	
	@RequestMapping(value="{purchaseReqId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("purchaseReqId")Long purchaseReqId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseReq purchaseReq = purchaseReqService.find(purchaseReqId);
		map.put("purchaseReq", purchaseReq);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(PurchaseReq.BUSI_CODE, purchaseReqId);
		map.put("opinionList", opinionList);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam("afteraction")String afteraction,@RequestParam("purchaseReqItemsId") Long[] purchaseReqItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseReq purchaseReq = purchaseReqService.find(id);
		
		List<PurchaseReqItem> purchaseReqItemList=BindTool.changeEntityItems(PurchaseReqItem.class, purchaseReq.getPurchaseReqItems(), purchaseReqItemsId);
		purchaseReq.setPurchaseReqItems(purchaseReqItemList);
		BindTool.bindRequest(purchaseReq, request);
		
		purchaseReq=purchaseReqService.save(purchaseReq);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			Result result =purchaseReqFacade.commitCheck(purchaseReq,user,taskId);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/checkedit",purchaseReq.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:/task/list";
			}	
		}
		
		return "";
	}
	
	
	
	
}
