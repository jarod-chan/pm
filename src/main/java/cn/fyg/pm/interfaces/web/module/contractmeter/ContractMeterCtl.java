package cn.fyg.pm.interfaces.web.module.contractmeter;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.service.BusifileService;
import cn.fyg.pm.application.service.ContractMeterService;
import cn.fyg.pm.application.service.ProjectService;
import cn.fyg.pm.application.service.PurchaseKeyService;
import cn.fyg.pm.application.service.PurchaseReqService;
import cn.fyg.pm.application.service.SupplierService;
import cn.fyg.pm.application.service.UserService;
import cn.fyg.pm.domain.model.contract.ContractRisk;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.ContractState;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.fileupload.busifile.Busifile;
import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.interfaces.web.module.purchasereq.ReqItemFacade;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("contractmeter")
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

	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		Project project=sessionUtil.getValue("project");
		List<ContractMeter> contractMeterList = contractMeterService.findByProject(project);
		List<ContractMeterDto> contractMeterDtoList = contractMeterAssembler.build(contractMeterList);
		map.put("contractMeterDtoList", contractMeterDtoList);
		return Page.LIST;
	}
	
	@RequestMapping(value="{contractMeterId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("contractMeterId") Long contractMeterId,Map<String,Object> map){
		Project project=sessionUtil.getValue("project");
		ContractMeter contractMeter=contractMeterId.longValue()>0?contractMeterService.find(contractMeterId):contractMeterService.create(project);
		map.put("contractMeter", contractMeter);
		map.put("supplierList", supplierService.findByTypeIn(Supptype.meter));
		map.put("contractStateList", ContractState.values());
		map.put("specialtyList", ContractSpec.values());
		map.put("contractRiskList", ContractRisk.values());
		List<PurchaseReq> purchaseReqList = purchaseReqService.findByProject(project);
		map.put("purchaseReqList", purchaseReqList);
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("id") Long contractMeterId,@RequestParam(value="filestore_id",required=false)Long[] filestore_id,@RequestParam(value="reqItemIds",required=false)Long[] reqItemIds,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project=sessionUtil.getValue("project");
		ContractMeter contractMeter=contractMeterId!=null?contractMeterService.find(contractMeterId):contractMeterService.create(project);
		
		ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(contractMeter);
		dataBinder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		dataBinder.bind(request);	
		
		if(contractMeter.getPurchaseKey()!=null&&contractMeter.getPurchaseKey().getId()==null){
			contractMeter.setPurchaseKey(null);
		}
		
		contractMeter=contractMeterService.save(contractMeter);
		if(filestore_id!=null){			
			BusiCode busiCode = BusiCode.pm_contractmeter;
			Long busiId=contractMeter.getId();
			reSaveBusifile(contractMeter, filestore_id, busiCode, busiId);
		}
		
		purchaseReqService.upReqItemList(UptypeEnum.pm_contractmeter, contractMeter.getId(), contractMeter.getNo(), reqItemIds);
		
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}

	private void reSaveBusifile(ContractMeter contractMeter, Long[] filestore_id,
			BusiCode busiCode, Long busiId) {
		List<Busifile> busifileList=new ArrayList<Busifile>();
		for (Long filestoreId : filestore_id) {
			Busifile busifile=new Busifile();
			busifile.setBusiCode(busiCode);
			busifile.setBusiId(contractMeter.getId());
			Filestore filestore=new Filestore();
			filestore.setId(filestoreId);
			busifile.setFilestore(filestore);
			busifileList.add(busifile);
		}
		this.busifileService.deleteByBusiCodeAndBusiId(busiCode, busiId);
		this.busifileService.save(busifileList);
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("contractMeterId") Long contractMeterId,RedirectAttributes redirectAttributes){
		this.busifileService.deleteByBusiCodeAndBusiId(BusiCode.pm_contractmeter, contractMeterId);
		purchaseReqService.rmReqItemList(UptypeEnum.pm_contractmeter, contractMeterId);
		contractMeterService.delete(contractMeterId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功"));
		return "redirect:list";
	}

}
