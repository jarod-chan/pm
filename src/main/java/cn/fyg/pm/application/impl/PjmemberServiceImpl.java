package cn.fyg.pm.application.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.pjrole.Pjrole;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

@Service("pjmemberService")
public class PjmemberServiceImpl implements PjmemberService {
	
	@Autowired
	PjmemberRepository pjmemberRepository;

	@Override
	public List<Project> getUserProject(User user) {
		List<Pjmember> pjmembers = this.pjmemberRepository.findByUser(user);
		List<Project> userProject=new ArrayList<Project>();
		for (Pjmember pjmember : pjmembers) {
			userProject.add(pjmember.getProject());
		}
		return userProject;
	}

	@Override
	public List<User> getProjectUser(Project project) {
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProject(project);
		List<User> projectUser = new ArrayList<User>();
		for (Pjmember pjmember : pjmembers) {
			projectUser.add(pjmember.getUser());
		}
		return projectUser;
	}

	@Override
	@Transactional
	public void appendPrjectUser(Project project, User user,Pjrole pjrole) {
		
		Preconditions.checkNotNull(project);
		Preconditions.checkNotNull(project.getId());
		Preconditions.checkNotNull(user);
		Preconditions.checkNotNull(user.getKey());
		Preconditions.checkNotNull(pjrole);
		Preconditions.checkNotNull(pjrole.getKey());
		
		Pjmember pjmember = this.pjmemberRepository.findByProjectAndUser(project, user);
		pjmember= pjmember==null?new Pjmember():pjmember;
		pjmember.setProject(project);
		pjmember.setUser(user);
		pjmember.setPjrole(pjrole);
		this.pjmemberRepository.save(pjmember);
	}

	@Override
	@Transactional
	public void removeProjectUser(Project project, User user) {
		Preconditions.checkNotNull(project);
		Preconditions.checkNotNull(project.getId());
		Preconditions.checkNotNull(user);
		Preconditions.checkNotNull(user.getKey());
		Pjmember pjmember = this.pjmemberRepository.findByProjectAndUser(project, user);
		if(pjmember==null){
			return;
		}
		this.pjmemberRepository.delete(pjmember);
	}

	@Override
	public Map<User, Pjrole> getProjectUserRole(Project project) {
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProject(project);
		Map<User, Pjrole> userToRole=new HashMap<User, Pjrole>();
		for (Pjmember pjmember : pjmembers) {
			userToRole.put(pjmember.getUser(), pjmember.getPjrole());
		}
		return userToRole;
	}
}
