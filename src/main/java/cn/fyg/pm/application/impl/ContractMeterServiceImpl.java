package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.ContractMeterService;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterFactory;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;

@Service("contractMeterService")
public class ContractMeterServiceImpl implements ContractMeterService {
	
	@Autowired
	ContractMeterRepository contractMeterRepository;
	@Autowired
	ContractMeterServiceExd contractMeterServiceExd;
	@Autowired
	@Qualifier("contractMeterNo")
	PatternFactory<ContractMeter> noFactory;
	@Autowired
	LockService lockService;


	@Override
	public ContractMeter find(Long id) {
		return this.contractMeterRepository.findOne(id);
	}


	@Override
	public ContractMeter create(Project project) {
		return ContractMeterFactory.create(project);
	}


	@Override
	public ContractMeter save(ContractMeter contractMeter) {
		Pattern<ContractMeter> pattern = noFactory.create(contractMeter).setEmpty(contractMeter.getId()!=null);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{			
			return this.contractMeterServiceExd.save(contractMeter,pattern);
		}finally{
			lock.unlock();
		}
	}


	@Override
	public void delete(Long contractMeterId) throws NoNotLastException {
		ContractMeter contractMeter = this.contractMeterRepository.findOne(contractMeterId);
		Pattern<ContractMeter> pattern = noFactory.create(contractMeter);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{			
			this.contractMeterServiceExd.delete(contractMeter,pattern);
		}finally{
			lock.unlock();
		}
	}


	@Override
	public Page<ContractMeter> findAll(Specification<ContractMeter> spec,
			Pageable pageable) {
		return this.contractMeterRepository.findAll(spec, pageable);
	}


	@Override
	public List<ContractMeter> findAll(Specification<ContractMeter> spec,
			Sort sort) {
		return this.contractMeterRepository.findAll(spec,sort);
	}

}
