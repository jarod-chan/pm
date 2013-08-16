package cn.fyg.pm.domain.model.design.designcont;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface DesignContRepository extends CrudRepository<DesignCont,Long>,RepositoryQuery<DesignCont> {

}
