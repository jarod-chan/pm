package cn.fyg.pm.interfaces.web.module.purchasereq;

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

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.interfaces.web.module.purchasereq.flow.ReqVarname;
import cn.fyg.pm.interfaces.web.module.purchasereq.query.ReqQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("purchasereq")
public class PurchaseReqCtl {
	
	private static final String PATH="purchasereq/";
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
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	OpinionService opinionService;
	@Autowired
	ReqItemFacade reqItemFacade;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(ReqQuery query,Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		query.setProject(project);
		List<PurchaseReq>  purchaseReqList= purchaseReqService.query(query);
		map.put("purchaseReqList", purchaseReqList);
		map.put("contractSpecList", ContractSpec.values());
		map.put("query", query);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.meter));
		return Page.LIST;
	}
	
	@RequestMapping(value="{purchaseReqId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("purchaseReqId")Long purchaseReqId,Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		PurchaseReq purchaseReq = purchaseReqId.longValue()>0?purchaseReqService.find(purchaseReqId):purchaseReqService.create(user,project,PurchaseReqState.new_);
		map.put("purchaseReq", purchaseReq);
		List<Contract> contractList = contractService.findByProjectAndType(purchaseReq.getPurchaseKey().getProject(),ContractType.meter);
		map.put("contractList", contractList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@RequestParam("id")Long purchaseReqId,@RequestParam("afteraction")String afteraction,@RequestParam(value="purchaseReqItemsId",required=false) Long[] purchaseReqItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		
		PurchaseReq purchaseReq =purchaseReqId!=null?purchaseReqService.find(purchaseReqId):purchaseReqService.create(user,project,PurchaseReqState.saved);
		 
		Map<Long,PurchaseReqItem> purchaseReqItemsMap=getPurchaseReqItemsMap(purchaseReq.getPurchaseReqItems());	
		List<PurchaseReqItem> purchaseReqItemList = new ArrayList<PurchaseReqItem>();
		for (int i = 0,len=purchaseReqItemsId==null?0:purchaseReqItemsId.length; i < len; i++) {
			PurchaseReqItem purchaseReqItem = purchaseReqItemsId[i]>0?purchaseReqItemsMap.get(purchaseReqItemsId[i]):new PurchaseReqItem();
			purchaseReqItemList.add(purchaseReqItem);
		}
		purchaseReq.setPurchaseReqItems(purchaseReqItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(purchaseReq);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		purchaseReq=purchaseReqService.save(purchaseReq);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			purchaseReq.setState(PurchaseReqState.commit);
			purchaseReq=purchaseReqService.save(purchaseReq);
			commit(purchaseReq, user);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
			return "redirect:list";
		}
		
		return "";
	}
	
	private Map<Long, PurchaseReqItem> getPurchaseReqItemsMap(List<PurchaseReqItem> purchaseReqItems) {
		Map<Long,PurchaseReqItem> map=new HashMap<Long, PurchaseReqItem>();
		if(purchaseReqItems==null) return map;
		for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
			map.put(purchaseReqItem.getId(), purchaseReqItem);
		}
		return map;
	}

	private void commit(PurchaseReq purchaseReq, User user) {
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, purchaseReq.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(ReqVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("purchaseReqId") Long purchaseReqId,RedirectAttributes redirectAttributes){
		purchaseReqService.delete(purchaseReqId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功！"));
		return "redirect:list";
	}

	@RequestMapping(value="{purchaseReqId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("purchaseReqId")Long purchaseReqId,Map<String,Object> map){
		PurchaseReq purchaseReq = purchaseReqService.find(purchaseReqId);
		map.put("purchaseReq", purchaseReq);
		return Page.VIEW;
	}
	
	@RequestMapping(value="{purchaseReqId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable(value="purchaseReqId")Long purchaseReqId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseReq purchaseReq = purchaseReqService.find(purchaseReqId);
		map.put("purchaseReq", purchaseReq);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		map.put("resultList", ResultEnum.agreeItems());
		return Page.CHECK;
	}
	
	@RequestMapping(value="check/commit",method=RequestMethod.POST)
	public String checkCommit(Opinion opinion,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		User user = sessionUtil.getValue("user");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		opinion.setBusiCode(PurchaseReq.BUSI_CODE);
		opinion.setTaskKey(task.getTaskDefinitionKey());
		opinion.setTaskName(task.getName());
		opinion.setUserKey(user.getKey());
		opinion.setUserName(user.getName());
		opinionService.append(opinion);
		runtimeService.setVariable(task.getProcessInstanceId(),getVarnameByTaskKey(opinion.getTaskKey()),opinion.getResult().val());
		runtimeService.setVariable(task.getProcessInstanceId(),ReqVarname.LAST_USERKEY,user.getKey());
		taskService.complete(task.getId());
		redirectAttributes
			.addFlashAttribute(AppConstant.MESSAGE_NAME,info("任务完成"));
		return "redirect:/task/list";
	}
	
	//TODO  根据任务类型来判断流程变量设置，待修改
	private String getVarnameByTaskKey(String taskKey){
		if(taskKey.equals("check-jszg")){
			return ReqVarname.OPINION_JSZG;
		}
		if(taskKey.equals("check-cbzg")){
			return ReqVarname.OPINION_CBZG;
		}
		
		return ReqVarname.OPINION;
	}
	
	@RequestMapping(value="{purchaseReqId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("purchaseReqId")Long purchaseReqId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseReq purchaseReq = purchaseReqService.find(purchaseReqId);
		map.put("purchaseReq", purchaseReq);
		List<Contract> contractList = contractService.findByProjectAndType(purchaseReq.getPurchaseKey().getProject(),ContractType.meter);
		map.put("contractList", contractList);
		map.put("taskId", taskId);
		List<Opinion> opinionList = opinionService.listOpinions(PurchaseReq.BUSI_CODE, purchaseReqId);
		map.put("opinionList", opinionList);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long id,@RequestParam("afteraction")String afteraction,@RequestParam("purchaseReqItemsId") Long[] purchaseReqItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseReq purchaseReq = purchaseReqService.find(id);
		
		Map<Long,PurchaseReqItem> purchaseReqItemsMap=getPurchaseReqItemsMap(purchaseReq.getPurchaseReqItems());	
		List<PurchaseReqItem> purchaseReqItemList = new ArrayList<PurchaseReqItem>();
		for (int i = 0,len=purchaseReqItemsId==null?0:purchaseReqItemsId.length; i < len; i++) {
			PurchaseReqItem purchaseReqItem = purchaseReqItemsId[i]>0?purchaseReqItemsMap.get(purchaseReqItemsId[i]):new PurchaseReqItem();
			purchaseReqItemList.add(purchaseReqItem);
		}
		purchaseReq.setPurchaseReqItems(purchaseReqItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(purchaseReq);
        binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		purchaseReq=purchaseReqService.save(purchaseReq);
		
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
	
	/**
	 * 供外部接口调用
	 */
	@RequestMapping(value="{purchaseKeyId}/items/{uptype}/{upid}",method=RequestMethod.GET)
	@ResponseBody 
	public List<ReqItemDto> getReqItemList(@PathVariable("purchaseKeyId")Long purchaseKeyId,@PathVariable("uptype")UptypeEnum uptype,@PathVariable("upid")Long upid){
		return reqItemFacade.getReqItemList(purchaseKeyId, uptype, upid);
	}
}
