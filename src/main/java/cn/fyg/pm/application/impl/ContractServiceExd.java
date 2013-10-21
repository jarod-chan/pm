package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;

@Component("contractServiceExd")
public class ContractServiceExd {
	
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	GeneService geneService;

	@Transactional
	public Contract save(Contract contract, Pattern<Contract> pattern) {
		this.geneService.generateNextNo(pattern);
		return this.contractRepository.save(contract);
	}

	@Transactional
	public void delete(Contract contract, Pattern<Contract> pattern) throws NoNotLastException {
		this.geneService.rollbackLastNo(pattern);
		this.contractRepository.delete(contract.getId());
	}

}
