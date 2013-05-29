package cn.fyg.pm.domain.model.purchase.purchasereq;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class PurchaseReqRepositoryImpl extends AbstractRepositoryQuery<PurchaseReq> implements RepositoryQuery<PurchaseReq> {

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


}
