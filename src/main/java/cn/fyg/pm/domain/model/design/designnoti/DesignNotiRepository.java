package cn.fyg.pm.domain.model.design.designnoti;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface DesignNotiRepository extends CrudRepository<DesignNoti,Long>,JpaSpecificationExecutor<DesignNoti>,RepositoryQuery<DesignNoti> {
	
	List<DesignNoti> findByProjectAndStateOrderByIdDesc(Project project,DesignNotiState state);


}
