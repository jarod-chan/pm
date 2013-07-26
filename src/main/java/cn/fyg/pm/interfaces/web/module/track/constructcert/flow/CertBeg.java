package cn.fyg.pm.interfaces.web.module.track.constructcert.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertBeg implements JavaDelegate{
	
	private Expression constructCertServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ConstructCertService constructCertService=(ConstructCertService)constructCertServiceExp.getValue(execution);
		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		ConstructCert constructCert = constructCertService.find(businessId);
		
		ContractSpec specialty = constructCert.getConstructKey().getContract().getSpecialty();
		execution.setVariable(CertVarname.SPECIALTY, specialty);
		Long projectId = constructCert.getConstructKey().getProject().getId();
		execution.setVariable(CertVarname.PROJECTID, projectId);

		opinionService.clear(ConstructCert.BUSI_CODE, businessId);
	}

}
