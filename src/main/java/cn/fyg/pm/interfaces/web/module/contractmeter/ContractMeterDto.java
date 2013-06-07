package cn.fyg.pm.interfaces.web.module.contractmeter;

import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;

public class ContractMeterDto {
	
	private ContractMeter contractMeter;
	
	private PurchaseReq purchaseReq;

	public ContractMeter getContractMeter() {
		return contractMeter;
	}

	public void setContractMeter(ContractMeter contractMeter) {
		this.contractMeter = contractMeter;
	}

	public PurchaseReq getPurchaseReq() {
		return purchaseReq;
	}

	public void setPurchaseReq(PurchaseReq purchaseReq) {
		this.purchaseReq = purchaseReq;
	}

	
}
