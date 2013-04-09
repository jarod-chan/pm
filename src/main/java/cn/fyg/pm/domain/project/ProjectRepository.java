package cn.fyg.pm.domain.project;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface ProjectRepository extends Repository<Project,Long> {
	
	Project save(Project project);

	List<Project> findAll();
	
	void delete(Long id);

}
