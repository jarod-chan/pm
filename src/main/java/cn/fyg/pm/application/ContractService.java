package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.contract.Contract;

public interface ContractService {
	
	List<Contract> findAll();
	
	Contract save(Contract contract);
	
	void delete(Long id);

}
