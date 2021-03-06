package cn.fyg.pm.interfaces.web.module.trace.designcont.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ContEnd implements JavaDelegate {
	
	private Expression designContServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		DesignContService designContService =(DesignContService) designContServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		String userKey=(String) execution.getVariable(FlowConstant.LAST_USERKEY);
		designContService.finish(businessId,userKey);
		
	}

}
