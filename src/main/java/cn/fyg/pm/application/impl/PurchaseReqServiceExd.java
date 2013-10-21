package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqRepository;

@Component("purchaseReqServiceExd")
public class PurchaseReqServiceExd {
	
	@Autowired
	PurchaseReqRepository purchaseReqRepository;
	@Autowired
	GeneService geneService;

	@Transactional
	public PurchaseReq save(PurchaseReq purchaseReq,Pattern<PurchaseReq> pattern) {
		this.geneService.generateNextNo(pattern);
		for(PurchaseReqItem purchaseReqItem:purchaseReq.getPurchaseReqItems()){
			purchaseReqItem.setPurchaseReq(purchaseReq);
		}
		return purchaseReqRepository.save(purchaseReq);
	}

	@Transactional
	public void finish(PurchaseReq purchaseReq, Pattern<PurchaseReq> pattern) {
		this.geneService.generateNextNo(pattern);
		this.purchaseReqRepository.save(purchaseReq);
	}

}
