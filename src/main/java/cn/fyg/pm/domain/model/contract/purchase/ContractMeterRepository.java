package cn.fyg.pm.domain.model.contract.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.project.Project;

public interface ContractMeterRepository extends CrudRepository<ContractMeter, Long>,JpaSpecificationExecutor<ContractMeter>{


	List<ContractMeter> findByProject(Project project);

	
}
