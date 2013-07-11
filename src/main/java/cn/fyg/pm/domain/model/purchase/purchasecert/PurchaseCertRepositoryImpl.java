package cn.fyg.pm.domain.model.purchase.purchasecert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class PurchaseCertRepositoryImpl extends AbstractRepositoryQuery<PurchaseCert> implements RepositoryQuery<PurchaseCert> {

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


}
