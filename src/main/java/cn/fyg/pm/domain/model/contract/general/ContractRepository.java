package cn.fyg.pm.domain.model.contract.general;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface ContractRepository extends Repository<Contract,Long>,JpaSpecificationExecutor<Contract>{
	
	Contract save(Contract contract);
	
	void delete(Long id);

	Contract findOne(Long id);
	
}
