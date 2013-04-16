package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.constructkey.ConstructKey;
import cn.fyg.pm.domain.project.Project;


public interface ConstructKeyService {
	
	List<ConstructKey> findByProject(Project project);

	ConstructKey save(ConstructKey constructKey);

	void delete(Long constructKeyID);

}
