package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.project.Project;

public interface ProjectService {
	
	List<Project> findAll();
	
	Project save(Project project);
	
	Project find(Long id);
	
	void delete(Long id);

}
