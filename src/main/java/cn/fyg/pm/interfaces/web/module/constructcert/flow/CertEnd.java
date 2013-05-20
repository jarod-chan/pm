package cn.fyg.pm.interfaces.web.module.constructcert.flow;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.domain.model.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.module.constructcont.flow.ContVarname;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertEnd implements JavaDelegate {
	
	private Expression constructCertServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ConstructCertService constructCertService=(ConstructCertService)constructCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		ConstructCert constructCert = constructCertService.find(businessId);
		String userKey=(String) execution.getVariable(ContVarname.LEADER_KEY);
		User leader=new User();
		leader.setKey(userKey);
		constructCert.setSigner(leader);
		constructCert.setSigndate(new Date());
		constructCert.setState(ConstructCertState.finish);
		constructCertService.save(constructCert);
	}

}
