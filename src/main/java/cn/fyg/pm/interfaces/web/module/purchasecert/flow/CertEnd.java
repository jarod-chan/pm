package cn.fyg.pm.interfaces.web.module.purchasecert.flow;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class CertEnd implements JavaDelegate {
	
	private Expression purchaseCertServiceExp;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		PurchaseCertService purchaseCertService =(PurchaseCertService) purchaseCertServiceExp.getValue(execution);
		Long businessId = (Long) execution.getVariableLocal(FlowConstant.BUSINESS_ID);
		PurchaseCert purchaseCert = purchaseCertService.find(businessId);
		String userKey=(String) execution.getVariable(CertVarname.LEADER_KEY);
		User leader=new User();
		leader.setKey(userKey);
		purchaseCert.setSigner(leader);
		purchaseCert.setSigndate(new Date());
		purchaseCert.setState(PurchaseCertState.finish);
		purchaseCertService.save(purchaseCert);
		
	}

}
