package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.SupplierRepository;

@Component("supplierServiceExd")
public class SupplierServiceExd {

	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	GeneService geneService;
	
	
	@Transactional
	public Supplier save(Supplier supplier, Pattern<Supplier> pattern) {
		this.geneService.generateNextNo(pattern);
		return this.supplierRepository.save(supplier);
	}

	@Transactional
	public void delete(Supplier supplier, Pattern<Supplier> pattern) throws NoNotLastException {
		this.geneService.rollbackLastNo(pattern);
		this.supplierRepository.delete(supplier.getId());
	}
	
	

}
