package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.project.Project;
import cn.fyg.pm.domain.project.ProjectRepository;

@Service
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

}
