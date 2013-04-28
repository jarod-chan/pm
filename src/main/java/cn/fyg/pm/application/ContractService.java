package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.project.Project;

public interface ContractService {
	
	List<Contract> findAll();
	
	Contract find(Long id);
	
	Contract save(Contract contract);
	
	void delete(Long id);
	
	List<Contract> findByProject(Project project);

}
