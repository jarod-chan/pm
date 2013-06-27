package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

public interface ContractService extends ServiceQuery<Contract> {
	
	List<Contract> findAll();
	
	List<Contract> findByProjectAndType(Project project,ContractType contractType);
	
	//TODO  可以重构为同意的规格查询
	List<Contract> findByProjectAndSupplierAndType(Project project,Supplier supplier,ContractType contractType);
	
	Contract find(Long id);
	
	Contract save(Contract contract);
	
	void delete(Long id);
	
	List<Contract> findByProject(Project project);

	Contract create(Project project);

	List<Contract> findBySupplier(Supplier supplier);
}
