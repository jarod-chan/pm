package cn.fyg.pm.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;

import cn.fyg.pm.application.common.CommitValidator;
import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.user.User;

public interface PurchaseReqService extends ServiceQuery<PurchaseReq>,CommitValidator<PurchaseReq> {

	PurchaseReq find(Long purchaseReqId);

	PurchaseReq create(User creater, Project project, PurchaseReqState state);

	PurchaseReq save(PurchaseReq purchaseReq);
	
	void finish(Long purchaseReqId,String userKey);
	
	List<PurchaseReq> findByProject(Project project,PurchaseReqState state);
	
	PurchaseReq findByPurchaseKey(PurchaseKey purchaseKey);

	void delete(Long purchaseReqId);
	
	void upReqItemList(UptypeEnum uptype,Long upid,String upno,Long[] upReqItemIds);
	 
	void rmReqItemList(UptypeEnum uptype,Long upid);

	Page<PurchaseReq> findAll(Specifications<PurchaseReq> specs,Pageable pageable);

	
}
