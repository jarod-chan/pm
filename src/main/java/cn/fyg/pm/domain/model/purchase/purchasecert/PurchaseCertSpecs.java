package cn.fyg.pm.domain.model.purchase.purchasecert;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.project.Project;

public class PurchaseCertSpecs {

	public static Specification<PurchaseCert> inProject(final Project project) {
		return new Specification<PurchaseCert>() {
			@Override
			public Predicate toPredicate(Root<PurchaseCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb
						.equal(root.get("purchaseKey").get("project"), project);
			}
		};
	}

	public static Specification<PurchaseCert> noLike(final String no) {
		return new Specification<PurchaseCert>() {
			@Override
			public Predicate toPredicate(Root<PurchaseCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String> get("no"), "%" + no + "%");
			}
		};
	}

	public static Specification<PurchaseCert> createAfterDate(final Date date) {
		return new Specification<PurchaseCert>() {
			@Override
			public Predicate toPredicate(Root<PurchaseCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.greaterThanOrEqualTo(root.<Date> get("createdate"),
						date);
			}
		};
	}

	public static Specification<PurchaseCert> createBeforeDate(final Date date) {
		return new Specification<PurchaseCert>() {
			@Override
			public Predicate toPredicate(Root<PurchaseCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb
						.lessThanOrEqualTo(root.<Date> get("createdate"), date);
			}
		};
	}

	public static Specification<PurchaseCert> isState(
			final PurchaseCertState state) {
		return new Specification<PurchaseCert>() {
			@Override
			public Predicate toPredicate(Root<PurchaseCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("state"), state);
			}
		};
	}

	public static Specification<PurchaseCert> notState(
			final PurchaseCertState state) {
		return new Specification<PurchaseCert>() {
			@Override
			public Predicate toPredicate(Root<PurchaseCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("state"), state);
			}
		};
	}

}
