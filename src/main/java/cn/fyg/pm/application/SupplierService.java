package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.supplier.Supplier;



public interface SupplierService {
	
	List<Supplier> findAll();
	
	Supplier save(Supplier supplier);
	
	void delete(Long id);

}
