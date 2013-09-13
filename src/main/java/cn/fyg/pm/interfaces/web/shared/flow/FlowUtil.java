package cn.fyg.pm.interfaces.web.shared.flow;

import java.util.List;
import java.util.Map;

import cn.fyg.pm.domain.model.workflow.opinion.Opinion;

public interface FlowUtil {
	
	void addRoleTasks(List<RoleTask> reloTasks);

	Map<String,Opinion> getCheckerOpinion(List<Opinion> opinions);
}
