package cn.fyg.pm.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;

public interface ContractService {
	
	Contract create(Project project);
	
	Contract find(Long id);
	
	Contract save(Contract contract);
	
	void delete(Long id) throws NoNotLastException;
	
	Page<Contract> findAll(Specification<Contract> spec, Pageable pageable);

	List<Contract> findAll(Specification<Contract> spec, Sort sort);
	
}
