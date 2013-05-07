package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface ConstructCertService {
	
	List<ConstructCert> findAll();
	
	ConstructCert save(ConstructCert constructCert);
	
	void delete(Long id);

	ConstructCert findByConstructKey(ConstructKey constructKey);
	
	List<ConstructCert> findByProject(Project project);

	ConstructCert find(Long certid);
	
	ConstructCert create(User user);

}
