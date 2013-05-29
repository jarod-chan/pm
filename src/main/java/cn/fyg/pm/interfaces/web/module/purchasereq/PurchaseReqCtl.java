package cn.fyg.pm.interfaces.web.module.purchasereq;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.service.ContractService;
import cn.fyg.pm.application.service.PurchaseReqService;
import cn.fyg.pm.application.service.SupplierService;
import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.ContractType;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqState;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.module.purchasereq.query.ReqQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.query.CommonQuery;
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
		map.put("stateList", CommonQuery.State.values());
		map.put("contractSpecList", ContractSpec.values());
		map.put("query", query);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.meter));
		return Page.LIST;
	}
	
	@RequestMapping(value="{purchaseReqId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("purchaseReqId")Long purchaseReqId,Map<String,Object> map){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		PurchaseReq purchaseReq = purchaseReqId.longValue()>0?purchaseReqService.find(purchaseReqId):purchaseReqService.create(user,project,PurchaseReqState.new_,false);
		map.put("purchaseReq", purchaseReq);
		List<Contract> contractList = contractService.findByProjectAndType(purchaseReq.getPurchaseKey().getProject(),ContractType.meter);
		map.put("contractList", contractList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="saveEdit",method=RequestMethod.POST)
	public String saveEdit(@RequestParam("id")Long purchaseReqId,@RequestParam("afteraction")String afteraction,@RequestParam(value="purchaseReqItemsId",required=false) Long[] purchaseReqItemsId,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project = sessionUtil.getValue("project");
		User user = sessionUtil.getValue("user");
		
		PurchaseReq purchaseReq =purchaseReqId!=null?purchaseReqService.find(purchaseReqId):purchaseReqService.create(user,project,PurchaseReqState.saved,true);
		 
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
		//TODO:补充填入供应商字段
		 PurchaseKey purchaseKey = purchaseReq.getPurchaseKey();
		if(purchaseKey.getContract()!=null){
			Contract contract = contractService.find(purchaseKey.getContract().getId());
			purchaseKey.setSupplier(contract.getSupplier());
		}
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
		// TODO Auto-generated method stub
		
	}



}
