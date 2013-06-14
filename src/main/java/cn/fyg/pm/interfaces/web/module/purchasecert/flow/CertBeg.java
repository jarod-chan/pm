package cn.fyg.pm.interfaces.web.module.purchasecert.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.service.OpinionService;
import cn.fyg.pm.application.service.PurchaseCertService;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertBeg implements JavaDelegate {
	
	private Expression purchaseCertServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		//TODO 临时流程设置
		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		PurchaseCertService purchaseCertService =(PurchaseCertService) purchaseCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		PurchaseCert purchaseCert = purchaseCertService.find(businessId);
		
		execution.setVariable(CertVarname.LEADER_KEY, "scfz");
		execution.setVariable(CertVarname.TOLSUM, purchaseCert.getTolsum());

		opinionService.clear(PurchaseReq.BUSI_CODE, businessId);

	}

}
