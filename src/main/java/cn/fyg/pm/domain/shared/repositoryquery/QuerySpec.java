package cn.fyg.pm.domain.shared.repositoryquery;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 *查询规格
 */
public interface QuerySpec<T> {
	
	List<Predicate> criterias(CriteriaBuilder builder,Root<T> from);
	
	List<Order> orders(CriteriaBuilder builder,Root<T> from);

}
