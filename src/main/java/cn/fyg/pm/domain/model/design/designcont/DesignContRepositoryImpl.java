package cn.fyg.pm.domain.model.design.designcont;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class DesignContRepositoryImpl extends AbstractRepositoryQuery<DesignCont> implements RepositoryQuery<DesignCont>  {

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
