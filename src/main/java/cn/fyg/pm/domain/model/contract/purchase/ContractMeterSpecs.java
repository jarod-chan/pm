package cn.fyg.pm.domain.model.contract.purchase;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

public class ContractMeterSpecs {
	
	  public static Specification<ContractMeter> inProject(final Project project) {
	    return new Specification<ContractMeter>() {
			@Override
			public Predicate toPredicate(Root<ContractMeter> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
			    return cb.equal(root.get("project"), project);
			}
		};
	  }


	public static Specification<ContractMeter> noLike(final String no){
		  return new Specification<ContractMeter>(){
			@Override
			public Predicate toPredicate(Root<ContractMeter> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get("no"), "%"+no +"%");
			}
		  };
	  }


	public static Specification<ContractMeter> nameLike(final String name){
		  return new Specification<ContractMeter>(){
			@Override
			public Predicate toPredicate(Root<ContractMeter> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.<String>get("name"), "%"+name.trim()+"%");
			}
		  };
	  }


	public static Specification<ContractMeter> isSpecialty(final ContractSpec contractSpec){
		  return new Specification<ContractMeter>(){
			@Override
			public Predicate toPredicate(Root<ContractMeter> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("specialty"), contractSpec);
			}
		  };
	  }
	  
	  public static Specification<ContractMeter> withSupplier(final Supplier supplier){
		  return new Specification<ContractMeter>(){
			@Override
			public Predicate toPredicate(Root<ContractMeter> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get("supplier"), supplier);
			}
		  };
	  }

}
