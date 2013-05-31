package cn.fyg.pm.interfaces.web.module.purchasecert;

import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReq;

public class PurchaseCertDto {
	
	private PurchaseCert purchaseCert;
	
	private PurchaseReq purchaseReq;

	public PurchaseCert getPurchaseCert() {
		return purchaseCert;
	}

	public void setPurchaseCert(PurchaseCert purchaseCert) {
		this.purchaseCert = purchaseCert;
	}

	public PurchaseReq getPurchaseReq() {
		return purchaseReq;
	}

	public void setPurchaseReq(PurchaseReq purchaseReq) {
		this.purchaseReq = purchaseReq;
	}
	
	

}
