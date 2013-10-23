package cn.fyg.pm.domain.model.user;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class UserSpecs {
	
	public static Specification<User> keyLike(final String key) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("key"), "%" + key + "%");
			}
		};
	}

	public static Specification<User> nameLike(final String name) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("name"), "%" + name + "%");
			}
		};
	}
	
	public static Specification<User> isEnabled(final EnabledEnum enabled) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("enabled"), enabled);
			}
		};
	}
}
