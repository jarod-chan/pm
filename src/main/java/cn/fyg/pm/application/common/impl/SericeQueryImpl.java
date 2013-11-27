package cn.fyg.pm.application.common.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.fyg.pm.application.common.ServiceQuery;

public abstract class SericeQueryImpl<T> implements ServiceQuery<T> {

	public abstract JpaSpecificationExecutor<T> getSpecExecutor();

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		JpaSpecificationExecutor<T> specExecutor = getSpecExecutor();
		return specExecutor.findAll(spec, pageable);
	}

	@Override
	public List<T> findAll(Specification<T> spec, Sort sort) {
		JpaSpecificationExecutor<T> specExecutor = getSpecExecutor();
		return specExecutor.findAll(spec, sort);
	}
}
