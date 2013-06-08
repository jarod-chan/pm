package cn.fyg.pm.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.service.ContractMeterService;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterFactory;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterRepository;
import cn.fyg.pm.domain.model.project.Project;

@Service("contractMeterService")
public class ContractMeterServiceImpl implements ContractMeterService {
	
	@Autowired
	ContractMeterRepository contractMeterRepository;


	@Override
	public List<ContractMeter> findByProject(Project project) {
		return this.contractMeterRepository.findByProject(project);
	}


	@Override
	public ContractMeter find(Long id) {
		return this.contractMeterRepository.findOne(id);
	}


	@Override
	public ContractMeter create(Project project) {
		return ContractMeterFactory.create(project);
	}


	@Override
	@Transactional
	public ContractMeter save(ContractMeter contractMeter) {
		return this.contractMeterRepository.save(contractMeter);
	}


	@Override
	public void delete(Long contractMeterId) {
		this.contractMeterRepository.delete(contractMeterId);
	}

}
