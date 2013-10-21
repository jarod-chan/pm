package cn.fyg.pm.interfaces.web.module.contractmeter;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.BusifileService;
import cn.fyg.pm.application.ContractMeterService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.PurchaseKeyService;
import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.contract.ContractRisk;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.ContractState;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.interfaces.web.module.contractmeter.query.ContractMeterQuery;
import cn.fyg.pm.interfaces.web.module.trace.purchasereq.ReqItemFacade;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/contractmeter")
public class ContractMeterCtl {
	
	private static final String PATH = "contractmeter/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	ContractMeterService contractMeterService;
	@Autowired
	ProjectService projectService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	BusifileService busifileService;
	@Autowired
	UserService userService;
	@Autowired
	PurchaseReqService purchaseReqService;
	@Autowired
	ContractMeterAssembler contractMeterAssembler;
	@Autowired
	ReqItemFacade reqItemFacade;
	@Autowired
	PurchaseKeyService purchaseKeyService;

	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(@PathVariable("projectId")Long projectId,ContractMeterQuery query,Map<String,Object> map){
		Project project=new Project();
		project.setId(projectId);
		query.setProject(project);
		List<ContractMeter> contractMeterList = contractMeterService.findAll(query.getSpec(), query.getSort());
		List<ContractMeterDto> contractMeterDtoList = contractMeterAssembler.build(contractMeterList);
		map.put("contractMeterDtoList", contractMeterDtoList);
		map.put("contractSpecList", ContractSpec.values());
		map.put("supplierList", supplierService.findByTypeIn(Supptype.meter));
		map.put("query", query);
		return Page.LIST;
	}
	
	@RequestMapping(value="{contractMeterId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("contractMeterId") Long contractMeterId,Map<String,Object> map){
		Project project=new Project();
		project.setId(projectId);
		ContractMeter contractMeter=contractMeterId.longValue()>0?contractMeterService.find(contractMeterId):contractMeterService.create(project);
		map.put("contractMeter", contractMeter);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.meter));
		map.put("contractStateList", ContractState.values());
		map.put("specialtyList", ContractSpec.values());
		map.put("contractRiskList", ContractRisk.values());
		map.put("userList", userService.findAll());
		List<PurchaseReq> purchaseReqList = purchaseReqService.findByProject(project,PurchaseReqState.finish);
		map.put("purchaseReqList", purchaseReqList);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(contractMeter.getPurchaseKey());
		map.put("purchaseReq", purchaseReq);
		if(contractMeter.getId()!=null){			
			BusiCode busiCode = ContractMeter.BUSI_CODE;
			Long busiId=contractMeter.getId();
			map.put("filestores", this.busifileService.findFilestores(busiCode, busiId));
		}
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@PathVariable("projectId")Long projectId,@RequestParam("id") Long contractMeterId,@RequestParam(value="filestore_id",required=false)Long[] filestore_id,@RequestParam(value="reqItemIds",required=false)Long[] reqItemIds,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project=projectService.find(projectId);
		ContractMeter contractMeter=contractMeterId!=null?contractMeterService.find(contractMeterId):contractMeterService.create(project);
		
		BindTool.bindRequest(contractMeter, request);
		
		if(contractMeter.getPurchaseKey()!=null&&contractMeter.getPurchaseKey().getId()==null){
			contractMeter.setPurchaseKey(null);
		}
		
		contractMeter=contractMeterService.save(contractMeter);
		
		BusiCode busiCode = ContractMeter.BUSI_CODE;
		Long busiId=contractMeter.getId();
		this.busifileService.associateFile(busiCode, busiId, filestore_id);
		
		purchaseReqService.upReqItemList(UptypeEnum.pm_contractmeter, contractMeter.getId(), contractMeter.getNo(), reqItemIds);
		
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}

	
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("contractMeterId") Long contractMeterId,RedirectAttributes redirectAttributes){
		//TODO 控制此处事务 
		try {
			this.contractMeterService.delete(contractMeterId);
			this.busifileService.removeAssociatedFile(BusiCode.pm_contractmeter, contractMeterId);
			this.purchaseReqService.rmReqItemList(UptypeEnum.pm_contractmeter, contractMeterId);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功"));
		} catch (NoNotLastException e) {
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info(e.getMessage()));
		}
		
		return "redirect:list";
	}

}
