package cn.fyg.pm.interfaces.web.shared.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class QueryComp<T> {
	
	private List<Specification<T>> specifications=new ArrayList<Specification<T>>();
	
	public QueryComp<T> addSpec(Specification<T> specification){
		specifications.add(specification);
		return this;
	}
	
	public Specification<T> toSpec() {
		Specifications<T> querySpec=null;
		if(!this.specifications.isEmpty()){
			Specification<T> specification = this.specifications.remove(0);
			querySpec=Specifications.where(specification);
			for (Specification<T> spec : this.specifications) {
				querySpec=querySpec.and(spec);
			}
		}
		return querySpec;
	}

}
