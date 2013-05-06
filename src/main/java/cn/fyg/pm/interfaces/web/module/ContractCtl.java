package cn.fyg.pm.interfaces.web.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.BusifileService;
import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.fileupload.busifile.Busifile;
import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.shared.BusiCode;

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
	@Autowired
	BusifileService busifileService;
	
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
	public String save(Contract contract,@RequestParam(value="filestore_id",required=false)Long[] filestore_id){
		contract=contractService.save(contract);
		if(filestore_id!=null){			
			BusiCode busiCode = BusiCode.pm_contract;
			Long busiId=contract.getId();
			reSaveBusifile(contract, filestore_id, busiCode, busiId);
		}
		return "redirect:list";
	}

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
	public String delete(@RequestParam("contractId") Long contractId){
		this.busifileService.deleteByBusiCodeAndBusiId(BusiCode.pm_contract, contractId);
		contractService.delete(contractId);
		return "redirect:list";
	}
}
