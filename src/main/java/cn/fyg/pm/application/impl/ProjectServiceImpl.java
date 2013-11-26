package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.application.common.impl.SericeQueryRefImpl;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.project.ProjectFactory;
import cn.fyg.pm.domain.model.project.ProjectRepository;

@Service("projectService")
public class ProjectServiceImpl  extends SericeQueryRefImpl<Project> implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	ProjectServiceExd projectServiceExd;
	@Autowired
	@Qualifier("projectNo")
	PatternFactory<Project> noFactory;
	@Autowired
	LockService lockService;
	

	@Override
	public JpaSpecificationExecutor<Project> getSpecExecutor() {
		return this.projectRepository;
	}

	
	@Override
	public Project save(Project project) {
		Pattern<Project> pattern = noFactory.create(project).setEmpty(project.getId()!=null);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			return this.projectServiceExd.save(project,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public void delete(Long id) throws NoNotLastException {
		Project project = this.projectRepository.findOne(id);
		Pattern<Project> pattern = noFactory.create(project);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			this.projectServiceExd.delete(project,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public Project find(Long id) {
		return projectRepository.findOne(id);
	}

	@Override
	public Project create() {
		return ProjectFactory.create();
	}

}
