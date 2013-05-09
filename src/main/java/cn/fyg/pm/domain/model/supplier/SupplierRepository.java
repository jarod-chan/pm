package cn.fyg.pm.domain.model.supplier;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface SupplierRepository extends Repository<Supplier,Long>{
	
	Supplier save(Supplier supplier);

	List<Supplier> findAll();
	
	void delete(Long id);

	Supplier findOne(Long supplierId);

	List<Supplier> findByType(Supptype supptype);

}
