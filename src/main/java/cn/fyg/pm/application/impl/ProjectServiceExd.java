package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator2.generator.GeneService;
import cn.fyg.pm.domain.model.nogenerator2.generator.PatternGene;
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
	@Qualifier("project_no")
	PatternGene<Project> noGene;
	
	@Autowired
	GeneService<Project> geneService;
	
	@Transactional
	public Project save(Project project) {
		if(project.getId()==null){
			geneService.generateNextNo(noGene,project);
		}
		return projectRepository.save(project);
	}

	
	@Transactional
	public void delete(Project project) throws NoNotLastException {
		this.geneService.rollbackLastNo(noGene,project);
		List<Pjmember> projectPjmembers = this.pjmemberRepository.findByProject(project);
		this.pjmemberRepository.delete(projectPjmembers);
		projectRepository.delete(project.getId());
	}

}
