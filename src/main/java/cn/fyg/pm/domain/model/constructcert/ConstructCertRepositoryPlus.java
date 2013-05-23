package cn.fyg.pm.domain.model.constructcert;

import java.util.List;

import cn.fyg.pm.domain.shared.QuerySpec;

public interface ConstructCertRepositoryPlus {

	List<ConstructCert> query(QuerySpec<ConstructCert> querySpec);

}