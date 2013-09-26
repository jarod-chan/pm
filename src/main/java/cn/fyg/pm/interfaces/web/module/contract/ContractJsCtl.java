package cn.fyg.pm.interfaces.web.module.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.module.contract.component.ContractFacade;
import cn.fyg.pm.interfaces.web.module.contract.component.ContractJsQuery;
import cn.fyg.pm.interfaces.web.module.contract.component.ContractSmp;
import cn.fyg.pm.interfaces.web.module.contract.component.PageData;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("{projectId}/contract")
public class ContractJsCtl {
	
	@Autowired
	ContractService contractService;
	
	@RequestMapping(value="select.json",method=RequestMethod.GET)
	@ResponseBody 
	public PageData<ContractSmp> simpleQuery(@PathVariable("projectId")Long projectId,ContractJsQuery query){
		Project project=new Project();
		project.setId(projectId);
		Pageable pageable= new PageRequest(query.getPage(),AppConstant.PAGE_SIZE);
		Page<Contract> page = this.contractService.findByNoLikeAndProjectAndType(query.getNo(),project,query.getContractType(), pageable);
		return ContractFacade.transfer(page);
	}

}
