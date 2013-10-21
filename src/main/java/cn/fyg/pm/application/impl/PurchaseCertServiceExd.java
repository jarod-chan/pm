package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertItem;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertRepository;

@Component("purchaseCertServiceExd")
public class PurchaseCertServiceExd {
	
	@Autowired
	PurchaseCertRepository purchaseCertRepository;
	@Autowired
	GeneService geneService;

	@Transactional
	public PurchaseCert save(PurchaseCert purchaseCert,Pattern<PurchaseCert> pattern) {
		this.geneService.generateNextNo(pattern);
		for (PurchaseCertItem purchaseCertItem : purchaseCert.getPurchaseCertItems()) {
			purchaseCertItem.setPurchaseCert(purchaseCert);
		}
		return purchaseCertRepository.save(purchaseCert);
	}

	@Transactional
	public void finish(PurchaseCert purchaseCert, Pattern<PurchaseCert> pattern) {
		this.geneService.generateNextNo(pattern);
		this.purchaseCertRepository.save(purchaseCert);
	}

}
