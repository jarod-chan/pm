package cn.fyg.pm.application.facade;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.designcont.flow.ContVarname;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Component
public class DesignContFacade {
	
	@Autowired
	DesignContService designContService;
	@Autowired
	IdentityService identityService;
	@Autowired
	TaskService taskService;
	@Autowired
	RuntimeService runtimeService;
	
	@Transactional
	public Result commit(DesignCont designCont, User user) {
		Result result = this.designContService.verifyForCommit(designCont);
		if(result.notPass()) return result;
		designCont.setState(DesignContState.commit);
		designCont=this.designContService.save(designCont);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, designCont.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(ContVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@Transactional
	public Result commitCheck(DesignCont designCont, User user,String taskId) {
		Result result = this.designContService.verifyForCommit(designCont);
		if(result.notPass()) return result;
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put(ContVarname.STATE, designCont.getState());
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId,variableMap);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}

}
