package cn.fyg.pm.interfaces.web.module.purchasecert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;

@Component
public class PurchaseCertAssembler {
	
	@Autowired
	PurchaseReqService purchaseReqService;
	
	public List<PurchaseCertDto> buildDto(List<PurchaseCert> purchaseCertList){
		List<PurchaseCertDto> dtoList=new ArrayList<PurchaseCertDto>();
		for (PurchaseCert purchaseCert:purchaseCertList) {
			PurchaseCertDto purchaseCertDto = new PurchaseCertDto();
			PurchaseReq purchaseReq = this.purchaseReqService.findByPurchaseKey(purchaseCert.getPurchaseKey());
			purchaseCertDto.setPurchaseCert(purchaseCert);
			purchaseCertDto.setPurchaseReq(purchaseReq);
			dtoList.add(purchaseCertDto);
		}
		return dtoList;
	}

}
