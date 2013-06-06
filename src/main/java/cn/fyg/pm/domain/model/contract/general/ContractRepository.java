package cn.fyg.pm.domain.model.contract.general;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.project.Project;

public interface ContractRepository extends Repository<Contract,Long> {
	
	Contract save(Contract contract);
	
	List<Contract> findAll();
	
	void delete(Long id);

	Contract findOne(Long id);
	
	List<Contract> findByProject(Project project);

	List<Contract> findByProjectAndType(Project project,ContractType contractType);
}
