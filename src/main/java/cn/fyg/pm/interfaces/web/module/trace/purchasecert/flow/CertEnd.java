package cn.fyg.pm.interfaces.web.module.trace.purchasecert.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertEnd implements JavaDelegate {
	
	private Expression purchaseCertServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		PurchaseCertService purchaseCertService =(PurchaseCertService) purchaseCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		String userKey=(String) execution.getVariable(FlowConstant.LAST_USERKEY);
		purchaseCertService.finish(businessId,userKey);
	}

}
