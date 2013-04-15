package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.pjmember.Pjmember;
import cn.fyg.pm.domain.project.Project;
import cn.fyg.pm.domain.user.User;

public interface PjmemberService {
	
	List<Pjmember> save(List<Pjmember> pjmemberList);
	
	List<Pjmember> findByProject(Project project);
	
	void deleteByProject(Project project);

	List<Project> findUserProject(User user);

}
