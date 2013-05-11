package cn.fyg.pm.interfaces.web.module.constructcont.flow;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class EndSet implements JavaDelegate {
	
	private Expression constructContServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		ConstructContService constructContService =(ConstructContService) constructContServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		ConstructCont constructCont = constructContService.find(businessId);
		String userKey=(String) execution.getVariable(ContVarname.LEADER_KEY);
		User leader=new User();
		leader.setKey(userKey);
		constructCont.setSigner(leader);
		constructCont.setSigndate(new Date());
		constructCont.setState(ConstructContState.finish);
		constructContService.save(constructCont);
		
	
	}

}
