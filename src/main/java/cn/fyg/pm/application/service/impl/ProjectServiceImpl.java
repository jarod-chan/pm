package cn.fyg.pm.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.service.ProjectService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.project.ProjectFactory;
import cn.fyg.pm.domain.model.project.ProjectRepository;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;

	@Override
	@Transactional
	public Project save(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		projectRepository.delete(id);
	}

	@Override
	public Project find(Long id) {
		return projectRepository.findOne(id);
	}

	@Override
	public Project create() {
		return ProjectFactory.create();
	}

	@Override
	public List<Project> query(QuerySpec<Project> querySpec) {
		return this.projectRepository.query(Project.class, querySpec);
	}

}
