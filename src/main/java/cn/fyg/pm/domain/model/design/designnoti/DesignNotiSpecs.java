package cn.fyg.pm.domain.model.design.designnoti;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.project.Project;

public class DesignNotiSpecs {
	
	  public static Specification<DesignNoti> inProject(final Project project) {
	    return new Specification<DesignNoti>() {
			@Override
			public Predicate toPredicate(Root<DesignNoti> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
			    return cb.equal(root.get("project"), project);
			}
		};
	  }
	  
	  public static Specification<DesignNoti> noLike(final String no){
		  return new Specification<DesignNoti>(){
			@Override
			public Predicate toPredicate(Root<DesignNoti> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get("no"), "%"+no +"%");
			}
		  };
	  }
	  
	  public static Specification<DesignNoti> isState(final DesignNotiState state){
		  return new Specification<DesignNoti>(){
			@Override
			public Predicate toPredicate(Root<DesignNoti> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("state"), state);
			}
		  };
	  }
	  
	  public static Specification<DesignNoti> notState(final DesignNotiState state){
		  return new Specification<DesignNoti>(){
			@Override
			public Predicate toPredicate(Root<DesignNoti> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("state"), state);
			}
		  };
	  }
	  
		public static Specification<DesignNoti> createAfterDate(final Date date) {
			return new Specification<DesignNoti>() {
				@Override
				public Predicate toPredicate(Root<DesignNoti> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.greaterThanOrEqualTo(root.<Date>get("createdate"), date);
				}
			};
		}
		
		public static Specification<DesignNoti> createBeforeDate(final Date date) {
			return new Specification<DesignNoti>() {
				@Override
				public Predicate toPredicate(Root<DesignNoti> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.lessThanOrEqualTo(root.<Date>get("createdate"), date);
				}
			};
		}
		
}
