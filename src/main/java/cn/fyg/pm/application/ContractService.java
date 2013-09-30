package cn.fyg.pm.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

public interface ContractService extends ServiceQuery<Contract> {
	
	Contract create(Project project);
	
	Contract find(Long id);
	
	Contract save(Contract contract);
	
	void delete(Long id) throws NoNotLastException;
	
	
	List<Contract> findBySupplier(Supplier supplier);
	
	Page<Contract> findAll(Specification<Contract> spec, Pageable pageable);
	
}
