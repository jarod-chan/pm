package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PurchaseKeyService;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKeyRepository;

@Service("purchaseKeyService")
public class PurchaseKeyServiceImpl implements PurchaseKeyService {
	
	@Autowired
	PurchaseKeyRepository purchaseKeyRepository;

	@Override
	@Transactional
	public PurchaseKey save(PurchaseKey purchaseKey) {
		return purchaseKeyRepository.save(purchaseKey);
	}

}
