package cn.fyg.pm.interfaces.web.module.contract;

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
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.contract.ContractRisk;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.ContractState;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.interfaces.web.module.contract.query.ContractQuery;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
@RequestMapping("{projectId}/contract/{contractType}")
public class ContractCtl {

	private static final String PATH = "contract/";
	private interface VIEW {
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
	public String toList(@PathVariable("projectId")Long projectId,ContractQuery query,@PathVariable("contractType")ContractType contractType,Map<String,Object> map){
		
		Project project=new Project();
		project.setId(projectId);
		query.setProject(project);
		query.setContractType(contractType);
		List<Contract> contractList = contractService.findAll(query.getSpec(), query.getSort());
		map.put("contractList", contractList);
		map.put("query", query);

		map.put("contractType", contractType);
		map.put("contractSpecList", ContractSpec.values());
		map.put("supplierList", getSupplierList(contractType));
		return VIEW.LIST;
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
	public String toEdit(@PathVariable("projectId")Long projectId,@PathVariable("contractType")ContractType contractType,@PathVariable("contractId") Long contractId,Map<String,Object> map){
		Project project=new Project();
		project.setId(projectId);
		Contract contract=contractId.longValue()>0?contractService.find(contractId):contractService.create(project);
		map.put("contractType", contractType);
		map.put("contract", contract);
		map.put("supplierList", getSupplierList(contractType));
		map.put("contractStateList", ContractState.values());
		map.put("specialtyList", ContractSpec.values());
		map.put("contractRiskList", ContractRisk.values());
		map.put("userList", userService.findAll());
		if(contract.getId()!=null){			
			BusiCode busiCode = Contract.BUSI_CODE;
			Long busiId=contract.getId();
			map.put("filestores", this.busifileService.findFilestores(busiCode, busiId));
		}
		return VIEW.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@PathVariable("projectId")Long projectId,@RequestParam("id") Long contractId,@RequestParam(value="filestore_id",required=false)Long[] filestore_id,HttpServletRequest request,RedirectAttributes redirectAttributes){
		Project project=this.projectService.find(projectId);
		Contract contract=contractId!=null?contractService.find(contractId):contractService.create(project);
		
		BindTool.bindRequest(contract, request);
		contract=contractService.save(contract);
		
		BusiCode busiCode =  Contract.BUSI_CODE;
		Long busiId=contract.getId();
		this.busifileService.associateFile(busiCode, busiId, filestore_id);
	
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}

	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("contractId") Long contractId,RedirectAttributes redirectAttributes){
		try {
			this.contractService.delete(contractId);
			this.busifileService.removeAssociatedFile(BusiCode.pm_contract, contractId);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("操作完成"));
		} catch (NoNotLastException e) {
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info(e.getMessage()));
		}
		return "redirect:list";
	}
	
}
