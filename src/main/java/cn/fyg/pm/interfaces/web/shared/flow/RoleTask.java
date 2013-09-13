package cn.fyg.pm.interfaces.web.shared.flow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.fyg.pm.domain.model.workflow.opinion.Opinion;

public class RoleTask {
	
	/**
	 * 用户角色关键字
	 */
	private String key;
	
	/**
	 * 角色对应流程任务的关键字
	 */
	private Set<String> keySet;

	public RoleTask(String key, String[] keyArray) {
		super();
		this.key = key;
		this.keySet = new HashSet<String>(Arrays.asList(keyArray));
	} 
	
	public boolean addCheckBeans(Map<String,Opinion> map,Opinion opinion){
		String taskKey=opinion.getTaskKey();
		if(keySet.contains(taskKey)){
			map.put(this.key, opinion);
			return true;
		}
		return false;
	}
	
}
