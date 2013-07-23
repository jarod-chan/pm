package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ContractService;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractFactory;
import cn.fyg.pm.domain.model.contract.general.ContractRepository;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("contractService")
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	ContractRepository contractRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;

	@Override
	public List<Contract> findAll() {
		return contractRepository.findAll();
	}

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
	public List<Contract> findByProject(Project project) {
		return contractRepository.findByProject(project);
	}

	@Override
	public Contract create(Project project) {
		return ContractFactory.create(project);
	}

	@Override
	public List<Contract> findByProjectAndType(Project project,ContractType contractType) {
		return contractRepository.findByProjectAndTypeOrderByIdDesc(project,contractType);
	}

	@Override
	public List<Contract> query(QuerySpec<Contract> querySpec) {
		return this.contractRepository.query(Contract.class, querySpec);
	}

	@Override
	public List<Contract> findBySupplier(Supplier supplier) {
		return this.contractRepository.findBySupplier(supplier);
	}

	@Override
	public List<Contract> findByProjectAndSupplierAndType(Project project,
			Supplier supplier, ContractType contractType) {
		return this.contractRepository.findByProjectAndSupplierAndType(project,supplier,contractType);
	}

}
