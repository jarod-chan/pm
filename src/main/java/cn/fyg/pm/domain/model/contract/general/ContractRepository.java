package cn.fyg.pm.domain.model.contract.general;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface ContractRepository extends Repository<Contract,Long>,RepositoryQuery<Contract> {
	
	Contract save(Contract contract);
	
	List<Contract> findAll();
	
	void delete(Long id);

	Contract findOne(Long id);
	
	List<Contract> findByProject(Project project);

	List<Contract> findByProjectAndTypeOrderByIdDesc(Project project,ContractType contractType);

	List<Contract> findBySupplier(Supplier supplier);

	List<Contract> findByProjectAndSupplierAndType(Project project,Supplier supplier, ContractType contractType);
}
