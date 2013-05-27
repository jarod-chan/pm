package cn.fyg.pm.domain.shared.repositoryquery.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public class AbstractRepositoryQuery<T> implements RepositoryQuery<T> {
	
	protected EntityManager entityManager;

	@Override
	public List<T> query(Class<T> entityClass, QuerySpec<T> querySpec) {
		CriteriaBuilder builder=this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query=builder.createQuery(entityClass);
		Root<T> from = query.from(entityClass);
		List<Predicate> criterias=querySpec.criterias(builder, from);
		List<Order> orders=querySpec.orders(builder, from);
		
		if(!criterias.isEmpty()){
			if(criterias.size()==1){
				query.where(criterias.get(0));
			}else{
				query.where(builder.and(criterias.toArray(new Predicate[0])));
			}
		}
		
		if(!orders.isEmpty()){
			query.orderBy(orders);
		}
		
		return entityManager.createQuery(query).getResultList();
	}

}
