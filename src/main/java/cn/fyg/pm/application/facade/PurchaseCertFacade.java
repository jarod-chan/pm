package cn.fyg.pm.application.facade;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.purchasecert.flow.CertVarname;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Component
public class PurchaseCertFacade {
	
	@Autowired
	PurchaseCertService purchaseCertService;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	
	@Transactional
	public Result commit(PurchaseCert purchaseCert, User user) {
		Result result = this.purchaseCertService.verifyForCommit(purchaseCert);
		if(result.notPass()) return result;
		purchaseCert.setState(PurchaseCertState.commit);
		purchaseCert=purchaseCertService.save(purchaseCert);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, purchaseCert.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(CertVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		 return result;
	}
	
	@Transactional
	public Result commitCheck(PurchaseCert purchaseCert,User user,String taskId){
		Result result = this.purchaseCertService.verifyForCommit(purchaseCert);
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
