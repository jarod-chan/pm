package cn.fyg.pm.application;

import java.util.List;
import java.util.Map;

import cn.fyg.pm.domain.model.pjrole.Pjrole;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

//TODO 添加用户角色，修改原有方法 
public interface PjmemberService {
	
	/**
	 * 返回某一角色的用户
	 * @param projectId
	 * @param pjroleKey
	 * @return
	 */
	String getUserKey(Long projectId,String pjroleKey);
	
	
	/**
	 * 获得用户参与项目
	 * @param user
	 * @return
	 */
	List<Project> getUserProject(User user);
	
	
	/**
	 * 获得项目成员
	 * @param project
	 * @return
	 */
	List<User> getProjectUser(Project project);
	
	/**
	 * 获得项目的用户以及角色
	 * @param project
	 * @return
	 */
	Map<User,Pjrole>getProjectUserRole(Project project); 
	
	/**
	 * 给项目添加成员
	 * @param project
	 * @param user
	 */
	void appendPrjectUser(Project project,User user,Pjrole pjrole);

	/**
	 * 
	 * @param project
	 * @param user
	 */
	void removeProjectUser(Project project,User user);
}
