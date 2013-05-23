package cn.fyg.pm.domain.model.constructcont;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.fyg.pm.domain.shared.QuerySpec;

public class ConstructContRepositoryImpl implements
		ConstructContRepositoryPlus {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ConstructCont> query(QuerySpec<ConstructCont> querySpec) {
		CriteriaBuilder builder=entityManager.getCriteriaBuilder();
		CriteriaQuery<ConstructCont> query=builder.createQuery(ConstructCont.class);
		Root<ConstructCont> from = query.from(ConstructCont.class);
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
