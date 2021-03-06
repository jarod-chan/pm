package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.CommitValidator;
import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface ConstructCertService extends ServiceQuery<ConstructCert>,CommitValidator<ConstructCert> {
	
	List<ConstructCert> findAll();
	
	ConstructCert save(ConstructCert constructCert);
	
	void finish(Long constructCertId,String userKey);
	
	void Invalid(Long constructCertId);
	
	void delete(Long id);

	ConstructCert findByConstructKey(ConstructKey constructKey);
	
	List<ConstructCert> findByProject(Project project);

	ConstructCert find(Long certid);
	
	ConstructCert create(User user,Project project,ConstructCertState state);
	

}
