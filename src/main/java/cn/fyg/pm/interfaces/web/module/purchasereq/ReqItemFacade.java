package cn.fyg.pm.interfaces.web.module.purchasereq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.application.service.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.UptypeEnum;

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
	
	public void upReqItemList(Long purchaseKeyId,UptypeEnum uptype,Long upid,String upno,boolean[] chk_val,boolean[] chk_read){
		PurchaseKey purchaseKey = new PurchaseKey();
		purchaseKey.setId(purchaseKeyId);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseKey);
		List<PurchaseReqItem> purchaseReqItems = purchaseReq.getPurchaseReqItems();
		if(purchaseReqItems!=null){			
			for(int i=0,len=purchaseReqItems.size();i<len;i++){
				if(chk_read[i]==false){
					if(chk_val[i]==true){
						purchaseReqItems.get(i).setUpid(upid);
						purchaseReqItems.get(i).setUpno(upno);
						purchaseReqItems.get(i).setUptype(uptype);
					}else{
						purchaseReqItems.get(i).setUpid(null);
						purchaseReqItems.get(i).setUpno(null);
						purchaseReqItems.get(i).setUptype(null);
					}
				}
			}
		}
		purchaseReqService.save(purchaseReq);
	}
	
	public void rmReqItemList(Long purchaseKeyId,UptypeEnum uptype,Long upid){
		PurchaseKey purchaseKey = new PurchaseKey();
		purchaseKey.setId(purchaseKeyId);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseKey);
		List<PurchaseReqItem> purchaseReqItems = purchaseReq.getPurchaseReqItems();
		for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
			if(uptype==purchaseReqItem.getUptype()&&upid.equals(purchaseReqItem.getUpid())){
				purchaseReqItem.setUpid(null);
				purchaseReqItem.setUpno(null);
				purchaseReqItem.setUptype(null);
			}
		}
		purchaseReqService.save(purchaseReq);
	}

}
