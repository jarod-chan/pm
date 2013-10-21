package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractFactory;
import cn.fyg.pm.domain.model.contract.general.ContractRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;

@Service("contractService")
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	@Qualifier("contractNo")
	PatternFactory<Contract> noFactory; 
	@Autowired
	ContractServiceExd contractServiceExd;
	@Autowired
	LockService lockService;

	@Override
	public Contract save(Contract contract) {
		Pattern<Contract> pattern = noFactory.create(contract).setEmpty(contract.getId()!=null);
		Lock lock = lockService.getLock(pattern);
		lock.lock();
		try{
			return this.contractServiceExd.save(contract,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public void delete(Long id) throws NoNotLastException {
		Contract contract =this.contractRepository.findOne(id);
		Pattern<Contract> pattern = noFactory.create(contract);
		Lock lock = lockService.getLock(pattern);
		lock.lock();
		try{
			this.contractServiceExd.delete(contract,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public Contract find(Long id) {
		return contractRepository.findOne(id);
	}

	@Override
	public Contract create(Project project) {
		return ContractFactory.create(project);
	}

	@Override
	public Page<Contract> findAll(Specification<Contract> spec, Pageable pageable){
		return this.contractRepository.findAll(spec, pageable);
	}
	
	@Override
	public List<Contract> findAll(Specification<Contract> spec, Sort sort){
		return this.contractRepository.findAll(spec, sort);
	}

}
