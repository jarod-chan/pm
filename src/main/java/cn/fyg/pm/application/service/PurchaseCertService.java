package cn.fyg.pm.application.service;

import cn.fyg.pm.application.shared.ServiceQuery;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.user.User;

public interface PurchaseCertService extends ServiceQuery<PurchaseCert> {

	PurchaseCert find(Long purchaseCertId);

	PurchaseCert create(User creater, Project project, PurchaseCertState state, boolean generateNo);
	
	PurchaseCert save(PurchaseCert purchaseCert);

	void delete(Long purchaseCertId);

}
