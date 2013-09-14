package cn.fyg.pm.application.facade;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.constructcert.flow.CertVarname;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Component
public class ConstructCertFacade {
	
	@Autowired
	ConstructCertService constructCertService;
	@Autowired
	IdentityService identityService;
	@Autowired
	TaskService taskService;
	@Autowired
	RuntimeService runtimeService;
	
	@Transactional
	public Result commit(ConstructCert constructCert, User user) {
		Result result = this.constructCertService.verifyForCommit(constructCert);
		if(result.notPass()) return result;
		constructCert.setState(ConstructCertState.commit);
		constructCert=constructCertService.save(constructCert);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, constructCert.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(CertVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@Transactional
	public Result commitCheck(ConstructCert constructCert,User user,String taskId){
		Result result = this.constructCertService.verifyForCommit(constructCert);
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
