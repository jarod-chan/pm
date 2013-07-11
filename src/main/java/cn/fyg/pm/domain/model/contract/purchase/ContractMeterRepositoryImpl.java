package cn.fyg.pm.domain.model.contract.purchase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;
import cn.fyg.pm.domain.shared.repositoryquery.impl.AbstractRepositoryQuery;

public class ContractMeterRepositoryImpl extends AbstractRepositoryQuery<ContractMeter> implements RepositoryQuery<ContractMeter>  {
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


}
