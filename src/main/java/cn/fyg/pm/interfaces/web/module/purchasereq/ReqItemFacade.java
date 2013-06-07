package cn.fyg.pm.interfaces.web.module.purchasereq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.application.service.PurchaseReqItemService;
import cn.fyg.pm.application.service.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;

@Component
public class ReqItemFacade {
	
	@Autowired
	PurchaseReqService purchaseReqService;
	@Autowired
	PurchaseReqItemService purchaseReqItemService;
	
	public List<ReqItemDto> getReqItemList(Long purchaseKeyId,UptypeEnum uptype,Long upid){
		PurchaseKey purchaseKey = new PurchaseKey();
		purchaseKey.setId(purchaseKeyId);
		PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(purchaseKey);
		List<PurchaseReqItem> purchaseReqItems = purchaseReq.getPurchaseReqItems();
		return ReqItemAssembler.build(purchaseReqItems,uptype,upid);
	}
	
	/**
	 * TODO 业务逻辑，重构到领域层
	 * @param uptype
	 * @param upid
	 * @param upno
	 * @param upReqItemIds
	 */
	public void upReqItemList(UptypeEnum uptype,Long upid,String upno,Long[] upReqItemIds){
		List<PurchaseReqItem> purchaseReqItems= purchaseReqItemService.findRelatedItems(uptype,upid); 
		if(purchaseReqItems!=null&&!purchaseReqItems.isEmpty()){
			for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
				purchaseReqItem.setUpid(null);
				purchaseReqItem.setUpno(null);
				purchaseReqItem.setUptype(null);
			}
			purchaseReqItemService.save(purchaseReqItems);
		}
		
		if(upReqItemIds!=null){
			purchaseReqItems= purchaseReqItemService.find(upReqItemIds);
			for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
				purchaseReqItem.setUpid(upid);
				purchaseReqItem.setUpno(upno);
				purchaseReqItem.setUptype(uptype);
			}
			purchaseReqItemService.save(purchaseReqItems);
		}
	
	}
	
	/**
	 * 待重构
	 * @param uptype
	 * @param upid
	 */
	public void rmReqItemList(UptypeEnum uptype,Long upid){
		List<PurchaseReqItem> purchaseReqItems= purchaseReqItemService.findRelatedItems(uptype,upid); 
		if(purchaseReqItems!=null&&!purchaseReqItems.isEmpty()){
			for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
				purchaseReqItem.setUpid(null);
				purchaseReqItem.setUpno(null);
				purchaseReqItem.setUptype(null);
			}
			purchaseReqItemService.save(purchaseReqItems);
		}
		
	}
	
	public void upReqItemList(Long purchaseKeyId,UptypeEnum uptype,Long upid,String upno,boolean[] chk_val,boolean[] chk_read){
		if(chk_val==null||chk_read==null){
			return;
		}
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
		if(purchaseReq!=null){
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

}
