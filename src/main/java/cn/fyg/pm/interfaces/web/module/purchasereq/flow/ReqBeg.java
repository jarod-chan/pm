package cn.fyg.pm.interfaces.web.module.purchasereq.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.service.OpinionService;
import cn.fyg.pm.application.service.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ReqBeg implements JavaDelegate {
	
	private Expression purchaseReqServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		PurchaseReqService purchaseReqService =(PurchaseReqService) purchaseReqServiceExp.getValue(execution);
		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		PurchaseReq purchaseReq = purchaseReqService.find(businessId);
		User leader = purchaseReq.getLeader();
		//TODO 模拟实现流程
		//execution.setVariable(ReqVarname.LEADER_KEY, leader.getKey());
		execution.setVariable(ReqVarname.LEADER_KEY, "xmb");

		opinionService.clear(PurchaseReq.BUSI_CODE, businessId);

	}

}
