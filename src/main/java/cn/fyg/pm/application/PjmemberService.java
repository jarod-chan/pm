package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface PjmemberService {
	
	
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
	 * 给项目添加成员
	 * @param project
	 * @param user
	 */
	void appendPrjectUser(Project project,User user);

	/**
	 * 
	 * @param project
	 * @param user
	 */
	void removeProjectUser(Project project,User user);
}
