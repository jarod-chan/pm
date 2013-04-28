package cn.fyg.pm.domain.model.constructcert;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.constructkey.ConstructKey;


public interface ConstructCertRepository extends Repository<ConstructCert, Long> {
	
	ConstructCert save(ConstructCert constructCert);
	
	List<ConstructCert> findAll();
	
	void delete(Long id);

	ConstructCert findByConstructKey(ConstructKey constructKey);

	ConstructCert findOne(Long certid);

}
