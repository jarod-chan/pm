package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;



public interface SupplierService extends ServiceQuery<Supplier> {
	
	List<Supplier> findAll();
	
	Supplier save(Supplier supplier);
	
	void delete(Long id) throws NoNotLastException;

	Supplier create();

	Supplier find(Long supplierId);
	
	List<Supplier> findByType(Supptype supptype);

	List<Supplier> findByTypeIn(Supptype... supptypes);
}
