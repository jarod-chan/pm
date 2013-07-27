package cn.fyg.pm.interfaces.web.module.purchasereq.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ReqEnd implements JavaDelegate {
	
	private Expression purchaseReqServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		PurchaseReqService purchaseReqService =(PurchaseReqService) purchaseReqServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		String userKey=(String) execution.getVariable(FlowConstant.LAST_USERKEY);
		purchaseReqService.finish(businessId,userKey);
	}

}
