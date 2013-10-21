package cn.fyg.pm.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;

public interface ContractMeterService {

	ContractMeter create(Project project);

	ContractMeter save(ContractMeter contractMeter);

	ContractMeter find(Long id);

	void delete(Long contractMeterId)throws NoNotLastException;
	
	Page<ContractMeter> findAll(Specification<ContractMeter> spec, Pageable pageable);

	List<ContractMeter> findAll(Specification<ContractMeter> spec, Sort sort);

}
