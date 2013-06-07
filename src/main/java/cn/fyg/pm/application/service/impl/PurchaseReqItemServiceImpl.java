package cn.fyg.pm.application.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.service.PurchaseReqItemService;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItemRepository;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;

@Service("purchaseReqItemService")
public class PurchaseReqItemServiceImpl implements PurchaseReqItemService {
	
	@Autowired
	PurchaseReqItemRepository purchaseReqItemRepository;

	@Override
	public List<PurchaseReqItem> findRelatedItems(UptypeEnum uptype, Long upid) {
		return purchaseReqItemRepository.findByUptypeAndUpid(uptype, upid);
	}

	@Override
	@Transactional
	public void save(List<PurchaseReqItem> purchaseReqItems) {
		 this.purchaseReqItemRepository.save(purchaseReqItems);
	}

	@Override
	public List<PurchaseReqItem> find(Long[] itemIds) {
		return (List<PurchaseReqItem>) this.purchaseReqItemRepository.findAll(Arrays.asList(itemIds));
	}

}
