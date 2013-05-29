package cn.fyg.pm.domain.model.construct.constructcont;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class ConstructContRepositoryImpl extends AbstractRepositoryQuery<ConstructCont> implements  RepositoryQuery<ConstructCont> {
	
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
