package cn.fyg.pm.interfaces.web.module.contractmeter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.application.service.PurchaseReqService;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReq;

@Component
public class ContractMeterAssembler {
	
	@Autowired
	PurchaseReqService purchaseReqService;
	

	public List<ContractMeterDto> build(List<ContractMeter> contractMeterList){
		ArrayList<ContractMeterDto> retList = new ArrayList<ContractMeterDto>();
		for (ContractMeter contractMeter : contractMeterList) {
			ContractMeterDto contractMeterDto = new ContractMeterDto();
			PurchaseReq purchaseReq = purchaseReqService.findByPurchaseKey(contractMeter.getPurchaseKey());
			contractMeterDto.setPurchaseReq(purchaseReq);
			contractMeterDto.setContractMeter(contractMeter);
			retList.add(contractMeterDto);
		}
		return retList;
	}
}
