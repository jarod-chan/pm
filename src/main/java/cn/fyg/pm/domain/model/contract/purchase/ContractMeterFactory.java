package cn.fyg.pm.domain.model.contract.purchase;

import cn.fyg.pm.domain.model.contract.ContractState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;

public class ContractMeterFactory {
	
	public static ContractMeter create(Project project){
		ContractMeter contractMeter = new ContractMeter();
		PurchaseKey purchaseKey=new PurchaseKey();
		purchaseKey.setProject(project);
		contractMeter.setPurchaseKey(purchaseKey);
		contractMeter.setState(ContractState.signed);
		return contractMeter;
	}

}
