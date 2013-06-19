package cn.fyg.pm.domain.model.supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class SupplierRepositoryImpl extends AbstractRepositoryQuery<Supplier> implements RepositoryQuery<Supplier> {

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
