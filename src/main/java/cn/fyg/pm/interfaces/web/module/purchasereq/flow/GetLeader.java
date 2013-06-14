package cn.fyg.pm.interfaces.web.module.purchasereq.flow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class GetLeader implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String leaderKey = (String) execution.getVariableLocal(ReqVarname.LEADER_KEY);
		leaderKey=getLeader(leaderKey);
		if(leaderKey.equals("scfz")){
			execution.setVariable(ReqVarname.IS_FINAL, true);
		}else{
			execution.setVariable(ReqVarname.IS_FINAL, false);
		}
		execution.setVariable(ReqVarname.LEADER_KEY, leaderKey);
	}

	private String getLeader(String leaderKey) {
		if(leaderKey.equals("xmb"))
			return "jsb";
		if(leaderKey.equals("jsb"))
			return "cbb";
		if(leaderKey.equals("cbb"))
			return "scfz";
		return "scfz";
	}

}
