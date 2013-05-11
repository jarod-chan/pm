package cn.fyg.pm.interfaces.web.module.constructcont.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class BeginSet implements JavaDelegate {
	
	private Expression constructContServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ConstructContService constructContService =(ConstructContService) constructContServiceExp.getValue(execution);
		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		ConstructCont constructCont = constructContService.find(businessId);
		User leader = constructCont.getLeader();
		execution.setVariable(ContVarname.LEADER_KEY, leader.getKey());

		opinionService.clear(ConstructCont.BUSI_CODE, businessId);

	}

}
