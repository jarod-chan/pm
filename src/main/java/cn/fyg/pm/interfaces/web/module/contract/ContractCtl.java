package cn.fyg.pm.interfaces.web.module.contract;

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

import cn.fyg.pm.application.BusifileService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.contract.ContractRisk;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.ContractState;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.fileupload.busifile.Busifile;
import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.interfaces.web.module.contract.query.ContractQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.CustomEditorFactory;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("contract/{contractType}")
public class ContractCtl {

	private static final String PATH = "contract/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	ContractService contractService;
	@Autowired
	ProjectService projectService;
	@Autowired
	SupplierService supplierService;
	@Autowired
	BusifileService busifileService;
	@Autowired
	UserService userService;

	@RequestMapping(value="list",method={RequestMethod.GET,RequestMethod.POST})
	public String toList(ContractQuery query,@PathVariable("contractType")ContractType contractType,Map<String,Object> map){
		Project project=sessionUtil.getValue("project");
		query.setProject(project);
		query.setContractType(contractType);
		List<Contract> contractList = contractService.query(query);
		map.put("contractType", contractType);
		map.put("contractList", contractList);
		map.put("contractSpecList", ContractSpec.values());
		map.put("supplierList", getSupplierList(contractType));
		map.put("query", query);
		return Page.LIST;
	}
	
	private List<Supplier> getSupplierList(ContractType contractType){
		if(ContractType.construct==contractType){
			return supplierService.findByTypeIn(Supptype.contra,Supptype.construct);
		}
		if(ContractType.design==contractType){
			return supplierService.findByTypeIn(Supptype.design);
		}
		if(ContractType.meter==contractType){
			return supplierService.findByTypeIn(Supptype.meter);
		}
		return null;
	}
	
	@RequestMapping(value="{contractId}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("contractType")ContractType contractType,@PathVariable("contractId") Long contractId,Map<String,Object> map){
		Project project=sessionUtil.getValue("project");
		Contract contract=contractId.longValue()>0?contractService.find(contractId):contractService.create(project);
		map.put("contractType", contractType);
		map.put("contract", contract);
		map.put("supplierList", getSupplierList(contractType));
		map.put("contractStateList", ContractState.values());
		map.put("specialtyList", ContractSpec.values());
		map.put("contractRiskList", ContractRisk.values());
		if(contract.getId()!=null){			
			BusiCode busiCode = BusiCode.pm_contract;
			Long busiId=contract.getId();
			map.put("filestores", this.busifileService.findFilestores(busiCode, busiId));
		}
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("id") Long contractId,@RequestParam(value="filestore_id",required=false)Long[] filestore_id,HttpServletRequest request,RedirectAttributes redirectAttributes){
		
		Contract contract=contractId!=null?contractService.find(contractId):contractService.create(null);
		
		ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(contract);
		dataBinder.registerCustomEditor(Date.class,CustomEditorFactory.getCustomDateEditor());
		dataBinder.bind(request);
		
		contract=contractService.save(contract);
		if(filestore_id!=null){			
			BusiCode busiCode = BusiCode.pm_contract;
			Long busiId=contract.getId();
			reSaveBusifile(contract, filestore_id, busiCode, busiId);
		}
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}

	//TODO  优化文件存储接口
	private void reSaveBusifile(Contract contract, Long[] filestore_id,
			BusiCode busiCode, Long busiId) {
		List<Busifile> busifileList=new ArrayList<Busifile>();
		for (Long filestoreId : filestore_id) {
			Busifile busifile=new Busifile();
			busifile.setBusiCode(busiCode);
			busifile.setBusiId(contract.getId());
			Filestore filestore=new Filestore();
			filestore.setId(filestoreId);
			busifile.setFilestore(filestore);
			busifileList.add(busifile);
		}
		this.busifileService.deleteByBusiCodeAndBusiId(busiCode, busiId);
		this.busifileService.save(busifileList);
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("contractId") Long contractId,RedirectAttributes redirectAttributes){
		this.busifileService.deleteByBusiCodeAndBusiId(BusiCode.pm_contract, contractId);
		contractService.delete(contractId);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("删除成功"));
		return "redirect:list";
	}
}
