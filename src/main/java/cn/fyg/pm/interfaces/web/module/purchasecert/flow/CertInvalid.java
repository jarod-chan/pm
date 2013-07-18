package cn.fyg.pm.interfaces.web.module.purchasecert.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertInvalid implements JavaDelegate {
	
	private Expression purchaseCertServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		PurchaseCertService purchaseCertService =(PurchaseCertService) purchaseCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		PurchaseCert purchaseCert = purchaseCertService.find(businessId);

		purchaseCert.setState(PurchaseCertState.invalid);
		purchaseCertService.save(purchaseCert);
		
	}

}
