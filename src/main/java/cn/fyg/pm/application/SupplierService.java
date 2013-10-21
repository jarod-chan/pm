package cn.fyg.pm.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
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
	
	Page<Supplier> findAll(Specification<Supplier> spec, Pageable pageable);

	List<Supplier> findAll(Specification<Supplier> spec, Sort sort);
}
