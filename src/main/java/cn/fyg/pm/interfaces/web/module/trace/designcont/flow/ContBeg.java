package cn.fyg.pm.interfaces.web.module.trace.designcont.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ContBeg implements JavaDelegate {
	
	private Expression designContServiceExp;
	
	private Expression opinionServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		DesignContService designContService =(DesignContService) designContServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		DesignCont designCont = designContService.find(businessId);
		
		execution.setVariable(FlowConstant.BUSINESS_NO, designCont.getNo());
		
		Project project =designCont.getProject();
		execution.setVariable(FlowConstant.PROJECT_ID, project.getId());
		execution.setVariable(FlowConstant.PROJECT_NAME, project.getName());

		OpinionService opinionService=(OpinionService) opinionServiceExp.getValue(execution);
		opinionService.clear(DesignNoti.BUSI_CODE, businessId);
	}

}
