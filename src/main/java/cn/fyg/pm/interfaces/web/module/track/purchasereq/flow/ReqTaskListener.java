package cn.fyg.pm.interfaces.web.module.track.purchasereq.flow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

public class ReqTaskListener implements TaskListener {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger=LoggerFactory.getLogger(ReqTaskListener.class);
	private Expression opinion_renameExp;

	@Override
	public void notify(DelegateTask delegateTask) {
		//跟流程配合实现技术主管和成本主管并行审批的功能
		Object tempOpinon=delegateTask.getVariable(FlowConstant.OPINION);
		String opinion_rename = (String)opinion_renameExp.getValue(delegateTask);
		delegateTask.setVariable(opinion_rename, tempOpinon);
		logger.info(opinion_rename);
	}

}
