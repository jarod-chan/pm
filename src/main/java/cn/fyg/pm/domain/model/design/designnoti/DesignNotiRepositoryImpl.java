package cn.fyg.pm.domain.model.design.designnoti;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class DesignNotiRepositoryImpl extends AbstractRepositoryQuery<DesignNoti> implements RepositoryQuery<DesignNoti>  {

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
