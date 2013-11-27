package cn.fyg.pm.domain.model.construct.constructcert;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;


public interface ConstructCertRepository extends Repository<ConstructCert, Long>, JpaSpecificationExecutor<ConstructCert> {
	
	ConstructCert save(ConstructCert constructCert);
	
	List<ConstructCert> findAll();
	
	void delete(Long id);

	ConstructCert findByConstructKey(ConstructKey constructKey);

	ConstructCert findOne(Long certid);
	
	List<ConstructCert> findByConstructKey_Project(Project project);

}
