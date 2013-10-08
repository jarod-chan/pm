package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractFactory;
import cn.fyg.pm.domain.model.contract.general.ContractRepository;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;

@Service("contractService")
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;

	@Override
	@Transactional
	public Contract save(Contract contract) {
		if(contract.getId()==null){
			noGeneratorBusi.generateNextNo(contract);
		}
		return contractRepository.save(contract);
	}

	@Override
	@Transactional
	public void delete(Long id) throws NoNotLastException {
		Contract contract =this.contractRepository.findOne(id);
		this.noGeneratorBusi.rollbackLastNo(contract);
		contractRepository.delete(id);
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
