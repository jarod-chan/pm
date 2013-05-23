package cn.fyg.pm.domain.shared.repositoryquery;

import java.util.List;

public interface RepositoryQuery<T> {

	List<T> query(Class<T> entityClass,QuerySpec<T> querySpec);
}
