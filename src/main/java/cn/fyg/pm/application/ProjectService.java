package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.project.Project;

public interface ProjectService {
	
	List<Project> findAll();
	
	Project save(Project project);
	
	void delete(Long id);

}
