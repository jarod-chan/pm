package cn.fyg.pm.interfaces.web.module.trace.designnoti.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class NotiEnd implements JavaDelegate {
	
	private Expression designNotiServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		DesignNotiService designNotiService =(DesignNotiService) designNotiServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		String userKey=(String) execution.getVariable(FlowConstant.LAST_USERKEY);
		designNotiService.finish(businessId,userKey);
		
	}

}
