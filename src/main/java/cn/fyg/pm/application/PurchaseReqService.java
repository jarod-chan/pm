package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.user.User;

public interface PurchaseReqService extends ServiceQuery<PurchaseReq> {

	PurchaseReq find(Long purchaseReqId);

	PurchaseReq create(User creater, Project project, PurchaseReqState state);

	PurchaseReq save(PurchaseReq purchaseReq);
	
	PurchaseReq finish(Long purchaseReqId,String userKey);
	
	List<PurchaseReq> findByProject(Project project,PurchaseReqState state);
	
	PurchaseReq findByPurchaseKey(PurchaseKey purchaseKey);

	void delete(Long purchaseReqId);
	
	void upReqItemList(UptypeEnum uptype,Long upid,String upno,Long[] upReqItemIds);
	 
	void rmReqItemList(UptypeEnum uptype,Long upid);

}
