package cn.fyg.pm.domain.model.purchase.purchasereq;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItemRepository;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;

@Service("purchaseReqBusi")
public class PurchaseReqBusi {
	
	@Autowired//TODO 把仓储移动到service
	PurchaseReqItemRepository purchaseReqItemRepository;
	
	/**
	 * 更新关联对象明细，反写采购数据
	 * @param uptype
	 * @param upid
	 * @param upno
	 * @param upReqItemIds
	 */
	public void upReqItemList(UptypeEnum uptype,Long upid,String upno,Long[] upReqItemIds){
		List<PurchaseReqItem> purchaseReqItems= this.purchaseReqItemRepository.findByUptypeAndUpid(uptype,upid); 
		if(purchaseReqItems!=null&&!purchaseReqItems.isEmpty()){
			for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
				purchaseReqItem.setUpid(null);
				purchaseReqItem.setUpno(null);
				purchaseReqItem.setUptype(null);
			}
			this.purchaseReqItemRepository.save(purchaseReqItems);
		}
		
		if(upReqItemIds!=null){
			purchaseReqItems= (List<PurchaseReqItem>) this.purchaseReqItemRepository.findAll(Arrays.asList(upReqItemIds));
			for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
				purchaseReqItem.setUpid(upid);
				purchaseReqItem.setUpno(upno);
				purchaseReqItem.setUptype(uptype);
			}
			this.purchaseReqItemRepository.save(purchaseReqItems);
		}
	}
	
	/**
	 * 清空关联对象信息，用于在删除关联对象时，更新相应反写信息
	 * @param uptype
	 * @param upid
	 */
	public void rmReqItemList(UptypeEnum uptype,Long upid){
		List<PurchaseReqItem> purchaseReqItems= this.purchaseReqItemRepository.findByUptypeAndUpid(uptype,upid); 
		if(purchaseReqItems!=null&&!purchaseReqItems.isEmpty()){
			for (PurchaseReqItem purchaseReqItem : purchaseReqItems) {
				purchaseReqItem.setUpid(null);
				purchaseReqItem.setUpno(null);
				purchaseReqItem.setUptype(null);
			}
			this.purchaseReqItemRepository.save(purchaseReqItems);
		}
		
	}

}
