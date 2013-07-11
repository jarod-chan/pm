package cn.fyg.pm.interfaces.web.module.purchasereq.flow;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ReqEnd implements JavaDelegate {
	
	private Expression purchaseReqServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		PurchaseReqService purchaseReqService =(PurchaseReqService) purchaseReqServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		PurchaseReq purchaseReq = purchaseReqService.find(businessId);
		String userKey=(String) execution.getVariable(ReqVarname.LEADER_KEY);
		User leader=new User();
		leader.setKey(userKey);
		purchaseReq.setSigner(leader);
		purchaseReq.setSigndate(new Date());
		purchaseReq.setState(PurchaseReqState.finish);
		purchaseReqService.finish(purchaseReq);
		
	}

}
