package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.SupplierService;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.SupplierFactory;
import cn.fyg.pm.domain.model.supplier.SupplierRepository;
import cn.fyg.pm.domain.model.supplier.Supptype;

@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	@Qualifier("supplierNo")
	PatternFactory<Supplier> noFactory;
	@Autowired
	LockService lockService;
	@Autowired
	SupplierServiceExd supplierServiceExd;


	@Override
	public Supplier save(Supplier supplier) {
		Pattern<Supplier> pattern = noFactory.create(supplier).setEmpty(supplier.getId()!=null);
		Lock lock=lockService.getLock(pattern);
		lock.lock();
		try{
			return this.supplierServiceExd.save(supplier,pattern);
		}finally{
			lock.unlock();
		}
		
	}

	@Override
	public void delete(Long id) throws NoNotLastException {
		Supplier supplier = this.supplierRepository.findOne(id);
		Pattern<Supplier> pattern = noFactory.create(supplier);
		Lock lock=lockService.getLock(pattern);
		lock.lock();
		try{
			this.supplierServiceExd.delete(supplier,pattern);
		}finally{
			lock.unlock();
		}
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
	public Page<Supplier> findAll(Specification<Supplier> spec,Pageable pageable) {
		return this.supplierRepository.findAll(spec, pageable);
	}

	@Override
	public List<Supplier> findAll(Specification<Supplier> spec, Sort sort) {
		return this.supplierRepository.findAll(spec, sort);
	}

}
