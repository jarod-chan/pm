package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.constructcert.ConstructCert;

public interface ConstructCertService {
	
	List<ConstructCert> findAll();
	
	ConstructCert save(ConstructCert constructCert);
	
	void delete(Long id);

}
