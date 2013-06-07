package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;

public interface PurchaseReqItemService {

	List<PurchaseReqItem> findRelatedItems(UptypeEnum uptype, Long upid);

	void save(List<PurchaseReqItem> purchaseReqItems);

	List<PurchaseReqItem> find(Long[] itemids);
	
	

}
