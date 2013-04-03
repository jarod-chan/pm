package cn.fyg.mb.domain.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.fyg.mb.infrastructure.persistence.ProjectMapper;

@Repository
public class ProjectRepository {
	
	@Autowired
	ProjectMapper projectMapper;
	
	void save(Project project){
		projectMapper.save(project);
	}

}
