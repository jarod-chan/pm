package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import java.util.Date;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.user.User;

public class PurchaseReqFactory {
	
	public static PurchaseReq create(User creater,Project project, PurchaseReqState state){
		PurchaseKey purchaseKey=new PurchaseKey();
		purchaseKey.setProject(project);
		
		PurchaseReq purchaseReq = new PurchaseReq();
		purchaseReq.setPurchaseKey(purchaseKey);
		purchaseReq.setState(state);
		purchaseReq.setCreater(creater);
		purchaseReq.setCreatedate(new Date());
		purchaseReq.setLeader(project.getLeader());
		return purchaseReq;
	}

}
