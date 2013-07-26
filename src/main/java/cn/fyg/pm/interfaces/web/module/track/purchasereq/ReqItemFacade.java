package cn.fyg.pm.interfaces.web.module.track.purchasereq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;

@Component
public class ReqItemFacade {
	
	@Autowired
	PurchaseReqService purchaseReqService;
	
	public List<ReqItemDto> getReqItemList(Long purchaseKeyId,UptypeEnum uptype,Long upid){
		PurchaseKey purchaseKey = new PurchaseKey();
		purchaseKey.setId(purchaseKeyId);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseKey);
		List<PurchaseReqItem> purchaseReqItems = purchaseReq.getPurchaseReqItems();
		return ReqItemAssembler.build(purchaseReqItems,uptype,upid);
	}

}
