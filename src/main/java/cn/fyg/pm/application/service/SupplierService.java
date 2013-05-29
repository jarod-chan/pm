package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;



public interface SupplierService {
	
	List<Supplier> findAll();
	
	Supplier save(Supplier supplier);
	
	void delete(Long id);

	Supplier create();

	Supplier find(Long supplierId);
	
	List<Supplier> findByType(Supptype supptype);

	List<Supplier> findByTypeIn(Supptype... supptypes);
}
