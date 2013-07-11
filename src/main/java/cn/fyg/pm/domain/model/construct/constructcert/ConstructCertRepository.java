package cn.fyg.pm.domain.model.construct.constructcert;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;


public interface ConstructCertRepository extends Repository<ConstructCert, Long>, RepositoryQuery<ConstructCert> {
	
	ConstructCert save(ConstructCert constructCert);
	
	List<ConstructCert> findAll();
	
	void delete(Long id);

	ConstructCert findByConstructKey(ConstructKey constructKey);

	ConstructCert findOne(Long certid);
	
	List<ConstructCert> findByConstructKey_Project(Project project);

}
