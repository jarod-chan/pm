package cn.fyg.pm.domain.model.contract.general;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

public class ContractSpecs {
	
	  public static Specification<Contract> inProject(final Project project) {
	    return new Specification<Contract>() {
			@Override
			public Predicate toPredicate(Root<Contract> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
			    return cb.equal(root.get("project"), project);
			}
		};
	  }
	  
	  public static Specification<Contract> isContractType(final ContractType contractType){
		  return new Specification<Contract>(){
			@Override
			public Predicate toPredicate(Root<Contract> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("type"), contractType);
			}
		  };
	  }
	  
	  public static Specification<Contract> noLike(final String no){
		  return new Specification<Contract>(){
			@Override
			public Predicate toPredicate(Root<Contract> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get("no"), "%"+no +"%");
			}
		  };
	  }
	  
	  public static Specification<Contract> withSupplier(final Supplier supplier){
		  return new Specification<Contract>(){
			@Override
			public Predicate toPredicate(Root<Contract> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("supplier"), supplier);
			}
		  };
	  }

}
