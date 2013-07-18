package cn.fyg.pm.interfaces.web.module.purchasereq.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.PurchaseReqService;
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
		
		//TODO 项目负责人获得方式修改
		User leader = purchaseReq.getLeader();
		execution.setVariable(ReqVarname.LEADER_KEY, leader.getKey());
	
		execution.setVariable(ReqVarname.PROJECTID, purchaseReq.getPurchaseKey().getProject().getId());
		opinionService.clear(PurchaseReq.BUSI_CODE, businessId);

	}

}
