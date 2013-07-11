package cn.fyg.pm.domain.model.project;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface ProjectRepository extends Repository<Project,Long>,RepositoryQuery<Project> {
	
	Project save(Project project);

	List<Project> findAll();
	
	void delete(Long id);

	Project findOne(Long id);

}
