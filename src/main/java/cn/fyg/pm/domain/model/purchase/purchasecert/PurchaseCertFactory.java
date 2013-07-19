package cn.fyg.pm.domain.model.purchase.purchasecert;

import java.math.BigDecimal;
import java.util.Date;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class PurchaseCertFactory {
	
	public static PurchaseCert create(User creater,User projectLeader,Project project, PurchaseCertState state){
		
		PurchaseCert purchaseCert = new PurchaseCert();
		purchaseCert.setState(state);
		purchaseCert.setCreater(creater);
		purchaseCert.setCreatedate(new Date());
		purchaseCert.setLeader(projectLeader);
		purchaseCert.setTolsum(new BigDecimal("0.00"));
		return purchaseCert;
	}

}
