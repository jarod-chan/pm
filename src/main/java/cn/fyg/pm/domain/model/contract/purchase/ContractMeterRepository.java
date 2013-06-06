package cn.fyg.pm.domain.model.contract.purchase;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.project.Project;

public interface ContractMeterRepository extends CrudRepository<ContractMeter, Long> {

	List<ContractMeter> findByPurchaseKey_Project(Project project);

	
}
