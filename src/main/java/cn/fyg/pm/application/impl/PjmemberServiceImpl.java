package cn.fyg.pm.application.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

@Service("pjmemberService")
public class PjmemberServiceImpl implements PjmemberService {
	
	@Autowired
	PjmemberRepository pjmemberRepository;

	@Override
	@Transactional
	public List<Pjmember> save(List<Pjmember> pjmemberList) {
		return (List<Pjmember>) pjmemberRepository.save(pjmemberList);
	}

	@Override
	public List<Pjmember> findByProject(Project project) {
		return pjmemberRepository.findByProject(project);
	}

	@Override
	@Transactional
	public void deleteByProject(Project project) {
		List<Pjmember> pjmemberList = pjmemberRepository.findByProject(project);
		pjmemberRepository.delete(pjmemberList);
	}

	@Override
	public List<Project> findUserProject(User user) {
		List<Pjmember> pjmemberList = pjmemberRepository.findByUser(user);
		ArrayList<Project> projectList = new ArrayList<Project>();
		for (Pjmember pjmember : pjmemberList) {
			projectList.add(pjmember.getProject());
		}
		return projectList;
	}
}
