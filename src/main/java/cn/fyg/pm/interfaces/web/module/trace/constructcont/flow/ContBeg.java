package cn.fyg.pm.interfaces.web.module.trace.constructcont.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ContBeg implements JavaDelegate {
	
	private Expression constructContServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ConstructContService constructContService =(ConstructContService) constructContServiceExp.getValue(execution);
		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		ConstructCont constructCont = constructContService.find(businessId);
		
		execution.setVariable(FlowConstant.BUSINESS_NO, constructCont.getNo());
		
		ContractSpec specialty = constructCont.getConstructKey().getContract().getSpecialty();
		execution.setVariable(ContVarname.SPECIALTY, specialty);
		
		Project project = constructCont.getConstructKey().getProject();
		execution.setVariable(FlowConstant.PROJECT_ID, project.getId());
		execution.setVariable(FlowConstant.PROJECT_NAME, project.getName());

		opinionService.clear(ConstructCont.BUSI_CODE, businessId);
	}

}
