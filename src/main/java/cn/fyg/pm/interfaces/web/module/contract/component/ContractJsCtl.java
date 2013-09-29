package cn.fyg.pm.interfaces.web.module.contract.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractSpecs;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.interfaces.web.shared.component.PageData;
import cn.fyg.pm.interfaces.web.shared.component.PageDataAssembler;
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
		Specifications<Contract> specs=Specifications.where(ContractSpecs.inProject(project))
				.and(ContractSpecs.isContractType(query.getContractType()))
				.and(ContractSpecs.noLike(query.getNo()));
		if(query.getSupplierId()!=null){
			Supplier supplier = new Supplier();
			supplier.setId(query.getSupplierId());
			specs=specs.and(ContractSpecs.withSupplier(supplier));
		}
		Pageable pageable= new PageRequest(query.getPage(),AppConstant.PAGE_SIZE,new Sort(new Order(Direction.DESC,"id")));
		Page<Contract> page = this.contractService.findAll(specs, pageable);
		return PageDataAssembler.transferData(page, new ContractTsf());
	}

}
