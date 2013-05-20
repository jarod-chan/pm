package cn.fyg.pm.domain.model.constructcert;

import java.util.List;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.module.constructcert.query.CertQuery;

public interface ConstructCertRepositoryPlus {

	List<ConstructCert> queryList(Project project, CertQuery certQuery);

}