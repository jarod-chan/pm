package cn.fyg.pm.domain.model.purchase.purchasecert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.user.User;

public class PurchaseCertFactory {
	
	public static PurchaseCert create(User creater,Project project, PurchaseCertState state,boolean generateNo){
		PurchaseKey purchaseKey=new PurchaseKey();
		purchaseKey.setProject(project);
		
		PurchaseCert purchaseCert = new PurchaseCert();
		if(generateNo){
			purchaseCert.setNo(UUID.randomUUID().toString().toUpperCase().substring(0,4));
		}
		purchaseCert.setPurchaseKey(purchaseKey);
		purchaseCert.setState(state);
		purchaseCert.setCreater(creater);
		purchaseCert.setCreatedate(new Date());
		purchaseCert.setLeader(project.getLeader());
		purchaseCert.setTolsum(new BigDecimal("0.00"));
		return purchaseCert;
	}

}
