package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.project.ProjectRepository;

@Service("projectServiceExd")
public class ProjectServiceExd {
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	PjmemberRepository pjmemberRepository;
	
	@Autowired
	NoGeneratorBusi noGeneratorBusi;
	
	@Transactional
	public Project save(Project project) {
		if(project.getId()==null){
			noGeneratorBusi.generateNextNo(project);
		}
		return projectRepository.save(project);
	}

	
	@Transactional
	public void delete(Project project) throws NoNotLastException {
		this.noGeneratorBusi.rollbackLastNo(project);
		List<Pjmember> projectPjmembers = this.pjmemberRepository.findByProject(project);
		this.pjmemberRepository.delete(projectPjmembers);
		projectRepository.delete(project.getId());
	}

}
