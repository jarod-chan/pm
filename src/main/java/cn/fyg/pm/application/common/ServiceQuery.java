package cn.fyg.pm.application.common;

import java.util.List;

import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

public interface ServiceQuery<T> {

	List<T> query(QuerySpec<T> querySpec);

}