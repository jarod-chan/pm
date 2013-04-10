package cn.fyg.pm.interfaces.web.module;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.contract.Contract;

@Controller
@RequestMapping("contract")
public class ContractCtl {

	private static final String PATH = "contract/";
	private interface Page {
		String LIST = PATH + "list";
		String EDIT = PATH + "edit";
	}
	
	@Autowired
	ContractService contractService;
	@Autowired
	ProjectService projectService;
	@Autowired
	SupplierService supplierService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<Contract> contractList = contractService.findAll();
		map.put("contractList", contractList);
		return Page.LIST;
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String toEdit(Map<String,Object> map){
		map.put("projectList", projectService.findAll());
		map.put("supplierList", supplierService.findAll());
		return Page.EDIT;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(Contract contract){
		contractService.save(contract);
		return "redirect:list";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public String delete(@RequestParam("contractId") Long contractId){
		contractService.delete(contractId);
		return "redirect:list";
	}
}
