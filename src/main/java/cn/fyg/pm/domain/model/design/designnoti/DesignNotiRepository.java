package cn.fyg.pm.domain.model.design.designnoti;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface DesignNotiRepository extends CrudRepository<DesignNoti,Long>,RepositoryQuery<DesignNoti> {

}
