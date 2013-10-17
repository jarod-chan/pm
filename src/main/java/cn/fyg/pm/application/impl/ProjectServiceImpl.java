package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecordService;
import cn.fyg.pm.domain.model.nogenerator2.generator.PatternGene;
import cn.fyg.pm.domain.model.nogenerator2.look.Lock;
import cn.fyg.pm.domain.model.nogenerator2.look.LockService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.project.ProjectFactory;
import cn.fyg.pm.domain.model.project.ProjectRepository;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	NoRecordService noRecordService;
	@Autowired
	ProjectServiceExd projectServiceExd;
	@Autowired
	@Qualifier("project_no")
	PatternGene<Project> noGene;
	@Autowired
	LockService lockService;

	private int idx=1;
	
	@Override
	public Project save(Project project) {
		this.idx=this.idx+1;
		noGene.init(project);
		NoPattern noPattern = noGene.getNoPattern();
		Lock lock = this.lockService.getLock(project.getId()==null,noPattern);
		lock.lock();
		try{
			if(this.idx%2==0){
				try {
					Thread.sleep(1000*10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return this.projectServiceExd.save(project);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	public void delete(Long id) throws NoNotLastException {
		Project project = this.projectRepository.findOne(id);
		noGene.init(project);
		NoPattern noPattern = noGene.getNoPattern();
		Lock lock = this.lockService.getLock(noPattern);
		lock.lock();
		try{
			this.projectServiceExd.delete(project);
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

	@Override
	public List<Project> query(QuerySpec<Project> querySpec) {
		return this.projectRepository.query(Project.class, querySpec);
	}

}
