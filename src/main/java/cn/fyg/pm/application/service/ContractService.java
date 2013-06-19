package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.application.shared.ServiceQuery;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.project.Project;

public interface ContractService extends ServiceQuery<Contract> {
	
	List<Contract> findAll();
	
	List<Contract> findByProjectAndType(Project project,ContractType contractType);
	
	Contract find(Long id);
	
	Contract save(Contract contract);
	
	void delete(Long id);
	
	List<Contract> findByProject(Project project);

	Contract create(Project project);


}
