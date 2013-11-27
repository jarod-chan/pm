package cn.fyg.pm.domain.model.project;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface ProjectRepository extends Repository<Project,Long>,JpaSpecificationExecutor<Project> {
	
	Project save(Project project);

	void delete(Long id);

	Project findOne(Long id);

}
