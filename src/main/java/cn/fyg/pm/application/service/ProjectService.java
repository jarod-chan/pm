package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.domain.model.project.Project;

public interface ProjectService {
	
	List<Project> findAll();
	
	Project create();
	
	Project save(Project project);
	
	Project find(Long id);
	
	void delete(Long id);

}
