package cn.fyg.pm.domain.model.supplier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SupplierSpecs {

	public static Specification<Supplier> noLike(final String no) {
		return new Specification<Supplier>() {
			@Override
			public Predicate toPredicate(Root<Supplier> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("no"), "%" + no + "%");
			}
		};
	}

	public static Specification<Supplier> nameLike(final String name) {
		return new Specification<Supplier>() {
			@Override
			public Predicate toPredicate(Root<Supplier> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("name"), "%" + name.trim()
						+ "%");
			}
		};
	}
	
	public static Specification<Supplier> typeIn(final Supptype[] types) {
		return new Specification<Supplier>() {
			@Override
			public Predicate toPredicate(Root<Supplier> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return root.get("type").in((Object[])types);
			}
		};
	}
}
