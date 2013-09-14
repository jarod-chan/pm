package cn.fyg.pm.application.facade;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;
import cn.fyg.pm.interfaces.web.module.trace.purchasereq.flow.ReqVarname;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Component
public class PurchaseReqFacade {
	
	@Autowired
	PurchaseReqService purchaseReqService;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	
	@Transactional
	public Result commit(PurchaseReq purchaseReq, User user) {
		Result result = this.purchaseReqService.verifyForCommit(purchaseReq);
		if(result.notPass()) return result;
		purchaseReq.setState(PurchaseReqState.commit);
		purchaseReq=purchaseReqService.save(purchaseReq);
		String userKey=user.getKey();
		try{
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put(FlowConstant.BUSINESS_ID, purchaseReq.getId());
			variableMap.put(FlowConstant.APPLY_USER, userKey);
			identityService.setAuthenticatedUserId(userKey);
			runtimeService.startProcessInstanceByKey(ReqVarname.PROCESS_DEFINITION_KEY, variableMap);			
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return result;
	}
	
	@Transactional
	public Result commitCheck(PurchaseReq purchaseReq,User user,String taskId){
		Result result = this.purchaseReqService.verifyForCommit(purchaseReq);
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
