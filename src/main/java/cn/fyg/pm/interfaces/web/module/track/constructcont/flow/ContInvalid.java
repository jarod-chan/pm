package cn.fyg.pm.interfaces.web.module.track.constructcont.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ContInvalid implements JavaDelegate {
	
	private Expression constructContServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		//TODO 重构到领域层
		ConstructContService constructContService =(ConstructContService) constructContServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		ConstructCont constructCont = constructContService.find(businessId);
		constructCont.setState(ConstructContState.invalid);
		constructContService.save(constructCont);
	}

}
