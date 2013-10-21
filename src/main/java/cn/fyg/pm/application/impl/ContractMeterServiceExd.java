package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;

@Component("contractMeterServiceExd")
public class ContractMeterServiceExd {
	
	@Autowired
	ContractMeterRepository contractMeterRepository;
	@Autowired
	GeneService geneService;
	
	@Transactional
	public ContractMeter save(ContractMeter contractMeter,
			Pattern<ContractMeter> pattern) {
		this.geneService.generateNextNo(pattern);
		return this.contractMeterRepository.save(contractMeter);
	}

	@Transactional
	public void delete(ContractMeter contractMeter,Pattern<ContractMeter> pattern) throws NoNotLastException {
		this.geneService.rollbackLastNo(pattern);
		this.contractMeterRepository.delete(contractMeter);
	}

	
}
