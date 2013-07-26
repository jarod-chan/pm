package cn.fyg.pm.interfaces.web.module.track.purchasecert.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertBeg implements JavaDelegate {
	
	private Expression purchaseCertServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		PurchaseCertService purchaseCertService =(PurchaseCertService) purchaseCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		PurchaseCert purchaseCert = purchaseCertService.find(businessId);
		
		execution.setVariable(CertVarname.PROJECTID, purchaseCert.getPurchaseKey().getProject().getId());
		execution.setVariable(CertVarname.TOLSUM, purchaseCert.getTolsum());

		opinionService.clear(PurchaseReq.BUSI_CODE, businessId);

	}

}
