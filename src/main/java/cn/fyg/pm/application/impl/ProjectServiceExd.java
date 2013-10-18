package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator2.generator3.Pattern;
import cn.fyg.pm.domain.model.nogenerator2.service.GeneService;
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
	GeneService geneService;
	
	@Transactional
	public Project save(Project project, Pattern<Project> pattern) {
		this.geneService.generateNextNo(pattern);
		return projectRepository.save(project);
	}

	
	@Transactional
	public void delete(Project project, Pattern<Project> pattern) throws NoNotLastException {
		this.geneService.rollbackLastNo(pattern);
		List<Pjmember> projectPjmembers = this.pjmemberRepository.findByProject(project);
		this.pjmemberRepository.delete(projectPjmembers);
		projectRepository.delete(project.getId());
	}

}
