package cn.fyg.pm.domain.contract;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface ContractRepository extends Repository<Contract,Long> {
	
	Contract save(Contract contract);
	
	List<Contract> findAll();
	
	void delete(Long id);

	Contract findOne(Long id);
}
