package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;


public interface ConstructKeyService {
	
	List<ConstructKey> findByProject(Project project);

	ConstructKey save(ConstructKey constructKey);

	void delete(Long constructKeyID);

	ConstructKey find(Long constructKeyId);

}
