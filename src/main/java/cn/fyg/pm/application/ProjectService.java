package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;

public interface ProjectService extends ServiceQuery<Project> {
	
	List<Project> findAll();
	
	Project create();
	
	Project save(Project project);
	
	Project find(Long id);
	
	void delete(Long id)throws NoNotLastException;

}
