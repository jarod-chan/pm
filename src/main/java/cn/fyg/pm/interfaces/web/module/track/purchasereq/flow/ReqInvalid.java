package cn.fyg.pm.interfaces.web.module.track.purchasereq.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ReqInvalid implements JavaDelegate {

	private Expression purchaseReqServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		PurchaseReqService purchaseReqService =(PurchaseReqService) purchaseReqServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		PurchaseReq purchaseReq = purchaseReqService.find(businessId);
		purchaseReq.setState(PurchaseReqState.invalid);
		purchaseReqService.save(purchaseReq);
	}
}
