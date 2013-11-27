package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.project.Project;

public class PurchaseReqSpecs {
	

	  public static Specification<PurchaseReq> inProject(final Project project) {
	    return new Specification<PurchaseReq>() {
			@Override
			public Predicate toPredicate(Root<PurchaseReq> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
			    return cb.equal(root.get("purchaseKey").get("project"), project);
			}
		};
	  }
	  
	  public static Specification<PurchaseReq> noLike(final String no){
		  return new Specification<PurchaseReq>(){
			@Override
			public Predicate toPredicate(Root<PurchaseReq> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get("no"), "%"+no +"%");
			}
		  };
	  }

	  public static Specification<PurchaseReq> isState(final PurchaseReqState state){
		  return new Specification<PurchaseReq>(){
			@Override
			public Predicate toPredicate(Root<PurchaseReq> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("state"), state);
			}
		  };
	  }
	  
	  public static Specification<PurchaseReq> notState(final PurchaseReqState state){
		  return new Specification<PurchaseReq>(){
			@Override
			public Predicate toPredicate(Root<PurchaseReq> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("state"), state);
			}
		  };
	  }
	  
		public static Specification<PurchaseReq> createAfterDate(final Date date) {
			return new Specification<PurchaseReq>() {
				@Override
				public Predicate toPredicate(Root<PurchaseReq> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.greaterThanOrEqualTo(root.<Date>get("createdate"), date);
				}
			};
		}
		
		public static Specification<PurchaseReq> createBeforeDate(final Date date) {
			return new Specification<PurchaseReq>() {
				@Override
				public Predicate toPredicate(Root<PurchaseReq> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.lessThanOrEqualTo(root.<Date>get("createdate"), date);
				}
			};
		}
	
}
