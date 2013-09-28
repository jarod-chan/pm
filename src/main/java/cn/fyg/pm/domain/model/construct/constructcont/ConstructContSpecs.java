package cn.fyg.pm.domain.model.construct.constructcont;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

public class ConstructContSpecs {

	public static Specification<ConstructCont> inProject(final Project project) {
		return new Specification<ConstructCont>() {
			@Override
			public Predicate toPredicate(Root<ConstructCont> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("constructKey").get("project"), project);
			}
		};
	}
	
	public static Specification<ConstructCont> isState(final ConstructContState state) {
		return new Specification<ConstructCont>() {
			@Override
			public Predicate toPredicate(Root<ConstructCont> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("state"), state);
			}
		};
	}
	
	public static Specification<ConstructCont> noLike(final String no ) {
		return new Specification<ConstructCont>() {
			@Override
			public Predicate toPredicate(Root<ConstructCont> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get("no"),"%"+no+"%" );
			}
		};
	}
	
	public static Specification<ConstructCont> withSupplier(final Supplier supplier) {
		return new Specification<ConstructCont>() {
			@Override
			public Predicate toPredicate(Root<ConstructCont> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("constructKey").get("supplier"), supplier);
			}
		};
	}
	
	public static Specification<ConstructCont> canBeSelectByConstructCert(final long constructcert_id) {
		return new Specification<ConstructCont>() {
			@Override
			public Predicate toPredicate(Root<ConstructCont> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				 return cb.or(
						 	cb.isNull(root.get("constructKey").get("constructcert_id")),
						 	cb.equal(root.get("constructKey").get("constructcert_id"), constructcert_id)
						 );
			}
		};
	}

}
