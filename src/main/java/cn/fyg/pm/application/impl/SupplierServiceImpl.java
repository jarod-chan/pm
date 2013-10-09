package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.SupplierFactory;
import cn.fyg.pm.domain.model.supplier.SupplierRepository;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;

	@Override
	public List<Supplier> findAll() {
		return supplierRepository.findAll();
	}

	@Override
	@Transactional
	public Supplier save(Supplier supplier) {
		if(supplier.getId()==null){
			noGeneratorBusi.generateNextNo(supplier);
		}
		return supplierRepository.save(supplier);
	}

	@Override
	@Transactional
	public void delete(Long id) throws NoNotLastException {
		Supplier supplier = this.supplierRepository.findOne(id);
		this.noGeneratorBusi.rollbackLastNo(supplier);
		supplierRepository.delete(id);
	}

	@Override
	public Supplier create() {
		return SupplierFactory.create();
	}

	@Override
	public Supplier find(Long supplierId) {
		return supplierRepository.findOne(supplierId);
	}

	@Override
	public List<Supplier> findByType(Supptype supptype) {
		return supplierRepository.findByType(supptype);
	}

	@Override
	public List<Supplier> findByTypeIn(Supptype... supptypes) {
		return supplierRepository.findByTypeIn(supptypes);
	}

	@Override
	public List<Supplier> query(QuerySpec<Supplier> querySpec) {
		return this.supplierRepository.query(Supplier.class, querySpec);
	}

	@Override
	public Page<Supplier> findAll(Specification<Supplier> spec,
			Pageable pageable) {
		return this.supplierRepository.findAll(spec, pageable);
	}

	@Override
	public List<Supplier> findAll(Specification<Supplier> spec, Sort sort) {
		return this.supplierRepository.findAll(spec, sort);
	}

}
