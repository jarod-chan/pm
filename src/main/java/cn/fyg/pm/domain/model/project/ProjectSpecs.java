package cn.fyg.pm.domain.model.project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecs {

	public static Specification<Project> noLike(final String no) {
		return new Specification<Project>() {
			@Override
			public Predicate toPredicate(Root<Project> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("no"), "%" + no + "%");
			}
		};
	}
	
	public static Specification<Project> nameLike(final String name) {
		return new Specification<Project>() {
			@Override
			public Predicate toPredicate(Root<Project> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("name"), "%" + name + "%");
			}
		};
	}

}
