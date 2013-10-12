package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ProjectService;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator2.NoRecord2;
import cn.fyg.pm.domain.model.nogenerator2.NoRecordService;
import cn.fyg.pm.domain.model.nogenerator2.look.Lock;
import cn.fyg.pm.domain.model.nogenerator2.look.LockService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
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
	LockService lockService;

	private int idx=1;
	
	@Override
	public Project save(Project project) {
		this.idx=this.idx+1;
		cn.fyg.pm.domain.model.nogenerator2.NoPattern noPattern = project.getNoPattern2();
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
//		cn.fyg.pm.domain.model.nogenerator2.NoPattern noPattern = project.getNoPattern2();
//		NoRecord2 noRecord = null;
//		try{
//			if(this.idx%2==0&&false){
//				try {
//					Thread.sleep(1000*10);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			noRecord = this.noRecordService.getNoRecord(noPattern);
//			String nextNo = noRecord.nextNo();
//			project.setNo(nextNo);
//			this.noRecordService.save(noRecord);			
//			return this.projectRepository.save(project);
//		}finally{
//			noRecord.freeNo();
//		}
		
		
//		if(project.getId()==null){
//			noGeneratorBusi.generateNextNo(project);
//		}
//		return projectRepository.save(project);
	}

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	public void delete(Long id) throws NoNotLastException {
		Project project = this.projectRepository.findOne(id);
		cn.fyg.pm.domain.model.nogenerator2.NoPattern noPattern = project.getNoPattern2();
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
