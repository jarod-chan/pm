package cn.fyg.pm.application;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.user.User;

public interface PurchaseCertService extends ServiceQuery<PurchaseCert> {

	PurchaseCert find(Long purchaseCertId);

	PurchaseCert create(User creater, Project project, PurchaseCertState state);
	
	PurchaseCert save(PurchaseCert purchaseCert);
	
	PurchaseCert finish(PurchaseCert purchaseCert);

	void delete(Long purchaseCertId);

}
