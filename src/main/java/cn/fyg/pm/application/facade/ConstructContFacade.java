package cn.fyg.pm.application.facade;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.constructcont.flow.ContVarname;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Component
public class ConstructContFacade {
	
	@Autowired
	ConstructContService constructContService;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	
	@Transactional
	public Result commit(ConstructCont constructCont, User user) {
		Result result = this.constructContService.verifyForCommit(constructCont);
		if(result.notPass()) return result;
		constructCont.setState(ConstructContState.commit);
		constructCont=this.constructContService.save(constructCont);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, constructCont.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(ContVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@Transactional
	public Result commitCheck(ConstructCont constructCont,User user,String taskId){
		Result result = this.constructContService.verifyForCommit(constructCont);
		if(result.notPass()) return result;
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}

}
