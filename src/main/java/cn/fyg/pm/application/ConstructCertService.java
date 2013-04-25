package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.constructcert.ConstructCert;
import cn.fyg.pm.domain.constructkey.ConstructKey;
import cn.fyg.pm.domain.user.User;

public interface ConstructCertService {
	
	List<ConstructCert> findAll();
	
	ConstructCert save(ConstructCert constructCert);
	
	void delete(Long id);

	ConstructCert findByConstructKey(ConstructKey constructKey);

	ConstructCert find(Long certid);
	
	ConstructCert create(User user);

}
