package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.application.shared.ServiceQuery;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqState;
import cn.fyg.pm.domain.model.user.User;

public interface PurchaseReqService extends ServiceQuery<PurchaseReq> {

	PurchaseReq find(Long purchaseReqId);

	PurchaseReq create(User creater, Project project, PurchaseReqState state, boolean generateNo);

	PurchaseReq save(PurchaseReq purchaseReq);
	
	List<PurchaseReq> findByProject(Project project);
	
	PurchaseReq findByPurchaseKey(PurchaseKey purchaseKey);

	void delete(Long purchaseReqId);

}
