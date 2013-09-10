package cn.fyg.pm.interfaces.web.module.trace.purchasereq.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
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
		
		execution.setVariable(FlowConstant.BUSINESS_NO, purchaseReq.getNo());
	
		Project project = purchaseReq.getPurchaseKey().getProject();
		execution.setVariable(FlowConstant.PROJECT_ID, project.getId());
		execution.setVariable(FlowConstant.PROJECT_NAME, project.getName());
		
		opinionService.clear(PurchaseReq.BUSI_CODE, businessId);

	}

}
