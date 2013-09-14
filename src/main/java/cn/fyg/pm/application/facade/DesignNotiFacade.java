package cn.fyg.pm.application.facade;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.designnoti.flow.NotiVarname;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Component
public class DesignNotiFacade {
	
	@Autowired
	DesignNotiService designNotiService;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	
	@Transactional
	public Result commit(DesignNoti designNoti, User user) {
		Result result = this.designNotiService.verifyForCommit(designNoti);
		if(result.notPass()) return result;
		designNoti.setState(DesignNotiState.commit);
		designNoti=this.designNotiService.save(designNoti);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, designNoti.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(NotiVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@Transactional
	public Result commitCheck(DesignNoti designNoti, User user, String taskId) {
		Result result = this.designNotiService.verifyForCommit(designNoti);
		if(result.notPass()) return result;
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put(NotiVarname.STATE, designNoti.getState());
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId,variableMap);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}

	@Transactional
	public void invalid(DesignNoti designNoti,User user,String taskId) {
		designNoti.setState(DesignNotiState.invalid);
		this.designNotiService.save(designNoti);
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put(NotiVarname.STATE, designNoti.getState());
		try{
			identityService.setAuthenticatedUserId(user.getKey());
			taskService.complete(taskId,variableMap);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
	}

}
