package cn.fyg.pm.application.common;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface ServiceQuery<T> {

	Page<T> findAll(Specification<T> spec, Pageable pageable);

	List<T> findAll(Specification<T> spec, Sort sort);

}
