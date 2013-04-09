package cn.fyg.pm.domain.supplier;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface SupplierRepository extends Repository<Supplier,Long>{
	
	Supplier save(Supplier supplier);

	List<Supplier> findAll();
	
	void delete(Long id);

}
