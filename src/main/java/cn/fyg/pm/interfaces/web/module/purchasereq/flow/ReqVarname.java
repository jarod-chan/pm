package cn.fyg.pm.interfaces.web.module.purchasereq.flow;

public interface ReqVarname {
	
	/**
	 * 流程名
	 */
	String PROCESS_DEFINITION_KEY="pm-purchasecert-req";

	/**
	 * 项目负责人
	 */
	String LEADER_KEY="leaderKey";
	
	/**
	 *审批意见
	 */
	String OPINION="opinion";

	/**
	 * 是否最上级领导
	 */
	String IS_FINAL = "isFinal";

}
