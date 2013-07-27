package cn.fyg.pm.interfaces.web.module.constructcert.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertEnd implements JavaDelegate {
	
	private Expression constructCertServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ConstructCertService constructCertService=(ConstructCertService)constructCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		String userKey=(String) execution.getVariable(FlowConstant.LAST_USERKEY);
		constructCertService.finish(businessId,userKey);
	}

}
