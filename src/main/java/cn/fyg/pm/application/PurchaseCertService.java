package cn.fyg.pm.application;

import cn.fyg.pm.application.common.CommitValidator;
import cn.fyg.pm.application.common.ServiceQueryRef;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.user.User;

public interface PurchaseCertService extends ServiceQueryRef<PurchaseCert>,CommitValidator<PurchaseCert> {

	PurchaseCert find(Long purchaseCertId);

	PurchaseCert create(User creater, Project project, PurchaseCertState state);
	
	PurchaseCert save(PurchaseCert purchaseCert);
	
	void finish(Long purchaseCertId,String userKey);

	void delete(Long purchaseCertId);

	void invalid(Long purchaseCertId);

}
