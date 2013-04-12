package cn.fyg.pm.domain.constructcert;

import java.util.List;

import org.springframework.data.repository.Repository;


public interface ConstructCertRepository extends Repository<ConstructCert, Long> {
	
	ConstructCert save(ConstructCert constructCert);
	
	List<ConstructCert> findAll();
	
	void delete(Long id);

}
