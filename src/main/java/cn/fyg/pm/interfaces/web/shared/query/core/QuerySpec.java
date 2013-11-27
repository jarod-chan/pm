package cn.fyg.pm.interfaces.web.shared.query.core;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface QuerySpec<T> {
	
	Specification<T> getSpec();
	
	Sort getSort();

}
