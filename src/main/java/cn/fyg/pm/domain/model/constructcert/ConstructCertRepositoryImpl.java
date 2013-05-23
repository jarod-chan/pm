package cn.fyg.pm.domain.model.constructcert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class ConstructCertRepositoryImpl  extends AbstractRepositoryQuery<ConstructCert> implements  RepositoryQuery<ConstructCert> {
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
