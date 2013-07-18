package cn.fyg.pm.interfaces.web.module.constructcert.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertInvalid implements JavaDelegate {
	
	private Expression constructCertServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		//TODO 	重构到领域层
		ConstructCertService constructCertService=(ConstructCertService)constructCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		ConstructCert constructCert = constructCertService.find(businessId);
		constructCert.setState(ConstructCertState.invalid);
		constructCertService.save(constructCert);
	}

}
