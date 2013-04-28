package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.contract.ContractRepository;
import cn.fyg.pm.domain.model.project.Project;

@Service
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	ContractRepository contractRepository;

	@Override
	public List<Contract> findAll() {
		return contractRepository.findAll();
	}

	@Override
	@Transactional
	public Contract save(Contract contract) {
		return contractRepository.save(contract);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		contractRepository.delete(id);
	}

	@Override
	public Contract find(Long id) {
		return contractRepository.findOne(id);
	}

	@Override
	public List<Contract> findByProject(Project project) {
		return contractRepository.findByProject(project);
	}

}
