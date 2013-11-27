package cn.fyg.pm.domain.model.construct.constructcert;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

public class ConstructCertSpecs {
	
	public static Specification<ConstructCert> inProject(final Project project) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("constructKey").get("project"), project);
			}
		};
	}
	
	public static Specification<ConstructCert> noLike(final String no ) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get("no"),"%"+no+"%" );
			}
		};
	}
	
	public static Specification<ConstructCert> withSupplier(final Supplier supplier) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("constructKey").get("supplier"), supplier);
			}
		};
	}
	
	public static Specification<ConstructCert> createAfterDate(final Date date) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.greaterThanOrEqualTo(root.<Date>get("createdate"), date);
			}
		};
	}
	
	public static Specification<ConstructCert> createBeforeDate(final Date date) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.lessThanOrEqualTo(root.<Date>get("createdate"), date);
			}
		};
	}

	public static Specification<ConstructCert> isSpecialty(final ContractSpec specialty) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("constructKey").get("contract").get("specialty"), specialty);
			}
		};
	}

	public static Specification<ConstructCert> isState(final ConstructCertState state) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("state"), state);
			}
		};
	}
	
	public static Specification<ConstructCert> notState(final ConstructCertState state) {
		return new Specification<ConstructCert>() {
			@Override
			public Predicate toPredicate(Root<ConstructCert> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("state"), state);
			}
		};
	}

}
