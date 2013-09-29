package cn.fyg.pm.domain.model.purchase.purchasereq.req;

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
	  
	  public static Specification<PurchaseReq> isState(final PurchaseReqState state){
		  return new Specification<PurchaseReq>(){
			@Override
			public Predicate toPredicate(Root<PurchaseReq> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("state"), state);
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

}
