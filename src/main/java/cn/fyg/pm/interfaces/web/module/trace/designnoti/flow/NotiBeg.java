package cn.fyg.pm.interfaces.web.module.trace.designnoti.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class NotiBeg implements JavaDelegate {
	
	private Expression designNotiServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		DesignNotiService designNotiService =(DesignNotiService) designNotiServiceExp.getValue(execution);
		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		DesignNoti designNoti = designNotiService.find(businessId);
		
		execution.setVariable(FlowConstant.BUSINESS_NO, designNoti.getNo());
		
		Project project =designNoti.getProject();
		execution.setVariable(FlowConstant.PROJECT_ID, project.getId());
		execution.setVariable(FlowConstant.PROJECT_NAME, project.getName());

		opinionService.clear(DesignNoti.BUSI_CODE, businessId);
	}

}
