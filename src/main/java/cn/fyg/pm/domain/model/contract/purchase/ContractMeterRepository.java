package cn.fyg.pm.domain.model.contract.purchase;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface ContractMeterRepository extends CrudRepository<ContractMeter, Long>,RepositoryQuery<ContractMeter> {


	List<ContractMeter> findByProject(Project project);

	
}
