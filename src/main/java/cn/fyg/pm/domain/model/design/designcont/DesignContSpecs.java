package cn.fyg.pm.domain.model.design.designcont;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.project.Project;

public class DesignContSpecs {
	
	 public static Specification<DesignCont> inProject(final Project project) {
		    return new Specification<DesignCont>() {
				@Override
				public Predicate toPredicate(Root<DesignCont> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				    return cb.equal(root.get("project"), project);
				}
			};
		  }
		  
		  public static Specification<DesignCont> noLike(final String no){
			  return new Specification<DesignCont>(){
				@Override
				public Predicate toPredicate(Root<DesignCont> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.like(root.<String>get("no"), "%"+no +"%");
				}
			  };
		  }
		  
		  public static Specification<DesignCont> isState(final DesignContState state){
			  return new Specification<DesignCont>(){
				@Override
				public Predicate toPredicate(Root<DesignCont> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.equal(root.get("state"), state);
				}
			  };
		  }
		  
		  public static Specification<DesignCont> notState(final DesignContState state){
			  return new Specification<DesignCont>(){
				@Override
				public Predicate toPredicate(Root<DesignCont> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.notEqual(root.get("state"), state);
				}
			  };
		  }
		  
			public static Specification<DesignCont> createAfterDate(final Date date) {
				return new Specification<DesignCont>() {
					@Override
					public Predicate toPredicate(Root<DesignCont> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						return cb.greaterThanOrEqualTo(root.<Date>get("createdate"), date);
					}
				};
			}
			
			public static Specification<DesignCont> createBeforeDate(final Date date) {
				return new Specification<DesignCont>() {
					@Override
					public Predicate toPredicate(Root<DesignCont> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						return cb.lessThanOrEqualTo(root.<Date>get("createdate"), date);
					}
				};
			}

}
