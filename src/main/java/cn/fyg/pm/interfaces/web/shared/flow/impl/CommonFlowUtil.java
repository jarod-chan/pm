package cn.fyg.pm.interfaces.web.shared.flow.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.interfaces.web.shared.flow.FlowUtil;
import cn.fyg.pm.interfaces.web.shared.flow.RoleTask;

public abstract class CommonFlowUtil implements FlowUtil {
	
	@Override
	public Map<String, Opinion> getCheckerOpinion(List<Opinion> opinions) {
		List<RoleTask> roleTasks = new ArrayList<RoleTask>();
		addRoleTasks(roleTasks);
		Map<String,Opinion> map=new HashMap<String,Opinion>();
		for (Opinion opinion : opinions) {
			for (RoleTask roleTask : roleTasks) {
				if(roleTask.addCheckBeans(map, opinion)){
					break;
				}
			}
		}
		return map;
	}

}
