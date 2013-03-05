package cn.fyg.mb.domain.project;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository {
	
	void save(Project project);

}
