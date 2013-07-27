package cn.fyg.pm.interfaces.web.module.purchasecert;

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
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertItem;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.ResultEnum;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.purchasecert.flow.CertVarname;
import cn.fyg.pm.interfaces.web.module.purchasecert.query.CertQuery;
import cn.fyg.pm.interfaces.web.module.purchasereq.ReqItemFacade;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("purchasecert")
public class PurchaseCertCtl {
	
	private static final String PATH="purchasecert/";
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
	PurchaseCertService purchaseCertService;
	@Autowired
	ContractService contractService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	PurchaseCertAssembler purchaseCertAssembler;
	@Autowired
	ReqItemFacade reqItemFacade;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	OpinionService opinionService;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, CustomEditorFactory.getCustomDateEditor());
	}
	
	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(CertQuery query,Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		query.setProject(project);
		List<PurchaseCert>  purchaseCertList= purchaseCertService.query(query);
		List<PurchaseCertDto> purchaseCertDtoList = purchaseCertAssembler.buildDto(purchaseCertList);
		map.put("purchaseCertDtoList", purchaseCertDtoList);
		map.put("contractSpecList", ContractSpec.values());
		map.put("query", query);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.meter));
		return Page.LIST;
	}
	
	@RequestMapping(value="{purchaseCertId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("purchaseCertId")Long purchaseCertId,Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		PurchaseCert purchaseCert = purchaseCertId.longValue()>0?purchaseCertService.find(purchaseCertId):purchaseCertService.create(user,project,PurchaseCertState.new_);
		map.put("purchaseCert", purchaseCert);
		List<PurchaseReq> purchaseReqList = purchaseReqService.findByProject(project,PurchaseReqState.finish);
		map.put("purchaseReqList", purchaseReqList);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseCert.getPurchaseKey());
		map.put("purchaseReq", purchaseReq);
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@RequestParam("id")Long purchaseCertId,@RequestParam(value="reqItemIds",required=false)Long[] reqItemIds,@RequestParam(value="purchaseCertItemsId",required=false) Long[] purchaseCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		PurchaseCert purchaseCert = purchaseCertId!=null?purchaseCertService.find(purchaseCertId):purchaseCertService.create(user, project,PurchaseCertState.saved);
		
		Map<Long,PurchaseCertItem> purchaseCertItemMap=getConstructCertMap(purchaseCert.getPurchaseCertItems());
		
		List<PurchaseCertItem> purchaseCertItemList = new ArrayList<PurchaseCertItem>();
		for(int i=0,len=purchaseCertItemsId==null?0:purchaseCertItemsId.length;i<len;i++){
			PurchaseCertItem purchaseCertItem=(purchaseCertItemsId[i]>0?purchaseCertItemMap.get(purchaseCertItemsId[i]):new PurchaseCertItem());
			purchaseCertItemList.add(purchaseCertItem);
		}
		purchaseCert.setPurchaseCertItems(purchaseCertItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(purchaseCert);
		binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		purchaseCert=purchaseCertService.save(purchaseCert);
		
		purchaseReqService.upReqItemList(UptypeEnum.pm_purchasecert, purchaseCert.getId(), purchaseCert.getNo(),reqItemIds);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:list";
		}
		if(afteraction.equals("commit")){
			Result result = commit(purchaseCert, user);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/edit",purchaseCert.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:list";
			}
		}
		
		return "";
	
	}

	@Transactional
	private Result commit(PurchaseCert purchaseCert, User user) {
		Result result = this.purchaseCertService.verifyForCommit(purchaseCert);
		if(result.notPass()) return result;
		purchaseCert.setState(PurchaseCertState.commit);
		purchaseCert=purchaseCertService.save(purchaseCert);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, purchaseCert.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(CertVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		 return result;
	}

	private Map<Long, PurchaseCertItem> getConstructCertMap(List<PurchaseCertItem> purchaseCertItems) {
		 Map<Long, PurchaseCertItem>  map=new HashMap<Long,PurchaseCertItem>();
		 for (PurchaseCertItem purchaseCertItem : purchaseCertItems) {
			 map.put(purchaseCertItem.getId(), purchaseCertItem);
		}
		return map;
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("purchaseCertId") Long purchaseCertId,RedirectAttributes redirectAttributes){
		purchaseReqService.rmReqItemList(UptypeEnum.pm_purchasecert, purchaseCertId);
		purchaseCertService.delete(purchaseCertId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功！"));
		return "redirect:list";
	}
	
	@RequestMapping(value="{purchaseCertId}/view",method=RequestMethod.GET)
	public String toView(@PathVariable("purchaseCertId")Long purchaseCertId,Map<String,Object> map){
		PurchaseCert purchaseCert = purchaseCertService.find(purchaseCertId);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseCert.getPurchaseKey());
		map.put("purchaseCert", purchaseCert);
		map.put("purchaseReq", purchaseReq);
		List<Opinion> opinions = opinionService.listOpinions(PurchaseCert.BUSI_CODE, purchaseCertId);
		map.put("opinions", opinions);

		return Page.VIEW;
	}
	
	@RequestMapping(value="{purchaseCertId}/check",method=RequestMethod.GET)
	public String toCheck(@PathVariable(value="purchaseCertId")Long purchaseCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseCert purchaseCert = purchaseCertService.find(purchaseCertId);
		map.put("purchaseCert", purchaseCert);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseCert.getPurchaseKey());
		map.put("purchaseReq", purchaseReq);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map.put("task", task);
		List<Opinion> opinions = opinionService.listOpinions(PurchaseCert.BUSI_CODE, purchaseCertId);
		map.put("opinions", opinions);
		map.put("resultList", ResultEnum.agreeItems());
		map.put("busiCode",PurchaseCert.BUSI_CODE );
		return Page.CHECK;
	}
	
	@RequestMapping(value="{purchaseCertId}/checkedit",method=RequestMethod.GET)
	public String toCheckEdit(@PathVariable("purchaseCertId")Long purchaseCertId,Map<String,Object> map,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseCert purchaseCert = purchaseCertService.find(purchaseCertId);
		map.put("purchaseCert", purchaseCert);
		List<PurchaseReq> purchaseReqList = purchaseReqService.findByProject(purchaseCert.getPurchaseKey().getProject(),PurchaseReqState.finish);
		map.put("purchaseReqList", purchaseReqList);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseCert.getPurchaseKey());
		map.put("purchaseReq", purchaseReq);
		List<Opinion> opinionList = opinionService.listOpinions(PurchaseCert.BUSI_CODE, purchaseCertId);
		map.put("opinionList", opinionList);
		
		map.put("taskId", taskId);
		return Page.CHECK_EDIT;
	}
	
	@RequestMapping(value="checkedit/save",method=RequestMethod.POST)
	public String saveCheckedit(@RequestParam("id")Long purchaseCertId,@RequestParam(value="reqItemIds",required=false)Long[] reqItemIds,@RequestParam(value="purchaseCertItemsId",required=false) Long[] purchaseCertItemsId,HttpServletRequest request,@RequestParam("afteraction")String afteraction,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId){
		PurchaseCert purchaseCert = purchaseCertService.find(purchaseCertId);
		
		Map<Long,PurchaseCertItem> purchaseCertItemMap=getConstructCertMap(purchaseCert.getPurchaseCertItems());
		
		List<PurchaseCertItem> purchaseCertItemList = new ArrayList<PurchaseCertItem>();
		for(int i=0,len=purchaseCertItemsId==null?0:purchaseCertItemsId.length;i<len;i++){
			PurchaseCertItem purchaseCertItem=(purchaseCertItemsId[i]>0?purchaseCertItemMap.get(purchaseCertItemsId[i]):new PurchaseCertItem());
			purchaseCertItemList.add(purchaseCertItem);
		}
		purchaseCert.setPurchaseCertItems(purchaseCertItemList);
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(purchaseCert);
		binder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		binder.bind(request);
		purchaseCert=purchaseCertService.save(purchaseCert);
		purchaseReqService.upReqItemList(UptypeEnum.pm_purchasecert, purchaseCert.getId(), purchaseCert.getNo(),reqItemIds);
		
		if(afteraction.equals("save")){
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功！"));
			return "redirect:/task/list";
		}
		if(afteraction.equals("commit")){
			User user = sessionUtil.getValue("user");
			Result result = commitCheck(purchaseCert,user,taskId);
			if(result.notPass()){
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, error("提交失败！"+result.message()));
				return String.format("redirect:%s/checkedit",purchaseCert.getId());
			}else{				
				redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("提交成功！"));
				return "redirect:/task/list";
			}	
		}
		
		return "";
	
	}
	
	@Transactional
	private Result commitCheck(PurchaseCert purchaseCert,User user,String taskId){
		Result result = this.purchaseCertService.verifyForCommit(purchaseCert);
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
