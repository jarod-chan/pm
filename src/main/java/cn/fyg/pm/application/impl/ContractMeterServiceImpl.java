package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ContractMeterService;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterFactory;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterRepository;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("contractMeterService")
public class ContractMeterServiceImpl implements ContractMeterService {
	
	@Autowired
	ContractMeterRepository contractMeterRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;

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
		if(contractMeter.getId()==null){
			noGeneratorBusi.generateNextNo(contractMeter);
		}
		return this.contractMeterRepository.save(contractMeter);
	}


	@Override
	public void delete(Long contractMeterId) throws NoNotLastException {
		ContractMeter contractMeter = this.contractMeterRepository.findOne(contractMeterId);
		this.noGeneratorBusi.rollbackLastNo(contractMeter);
		this.contractMeterRepository.delete(contractMeterId);
	}


	@Override
	public List<ContractMeter> query(QuerySpec<ContractMeter> querySpec) {
		return this.contractMeterRepository.query(ContractMeter.class, querySpec);
	}

}
