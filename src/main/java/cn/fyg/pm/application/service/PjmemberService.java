package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface PjmemberService {
	
	List<Pjmember> save(List<Pjmember> pjmemberList);
	
	List<Pjmember> findByProject(Project project);
	
	void deleteByProject(Project project);

	List<Project> findUserProject(User user);

}
