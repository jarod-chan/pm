package cn.fyg.pm.domain.model.purchase.purchasereq;

import java.util.Date;
import java.util.UUID;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.user.User;

public class PurchaseReqFactory {
	
	public static PurchaseReq create(User creater,Project project, PurchaseReqState state,boolean generateNo){
		PurchaseKey purchaseKey=new PurchaseKey();
		purchaseKey.setProject(project);
		
		PurchaseReq purchaseReq = new PurchaseReq();
		if(generateNo){
			purchaseReq.setNo(UUID.randomUUID().toString().toUpperCase().substring(0,4));
		}
		purchaseReq.setPurchaseKey(purchaseKey);
		purchaseReq.setState(state);
		purchaseReq.setCreater(creater);
		purchaseReq.setCreatedate(new Date());
		purchaseReq.setLeader(project.getLeader());
		return purchaseReq;
	}

}
