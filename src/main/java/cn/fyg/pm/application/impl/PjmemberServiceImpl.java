package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.domain.pjmember.Pjmember;
import cn.fyg.pm.domain.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.project.Project;

@Service
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

}
