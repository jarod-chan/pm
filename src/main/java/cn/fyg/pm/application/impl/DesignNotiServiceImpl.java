package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiCommitVld;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiFactory;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiRepository;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.domain.shared.verify.Result;

@Service("designNotiService")
public class DesignNotiServiceImpl implements DesignNotiService {

	@Autowired
	DesignNotiRepository designNotiRepository;
	@Autowired
	PjmemberRepository pjmemberRepository;
	@Autowired
	DesignNotiServiceExd designNotiServiceExd;
	@Autowired
	LockService lockService;
	@Autowired
	@Qualifier("designNotiNo")
	PatternFactory<DesignNoti> noFactory;
	@Autowired
	@Qualifier("designNotiBusino")
	PatternFactory<DesignNoti> businoFactory;
	
	@Override
	public List<DesignNoti> query(QuerySpec<DesignNoti> querySpec) {
		return this.designNotiRepository.query(DesignNoti.class, querySpec);
	}

	@Override
	public Result verifyForCommit(DesignNoti designNoti) {
		DesignNotiCommitVld vld = new DesignNotiCommitVld();
		vld.setValObject(designNoti);
		return vld.verify();
	}

	@Override
	public DesignNoti find(Long designNotiId) {
		return this.designNotiRepository.findOne(designNotiId);
	}

	@Override
	public DesignNoti create(User creater, Project project,
			DesignNotiState state) {
		Role pjrole = new Role();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndRole(project, pjrole);
		User xmfzr = pjmembers.get(0).getUser();
		return DesignNotiFactory.create(creater,xmfzr,project,state);
	}

	@Override
	public DesignNoti save(DesignNoti designNoti) {
		Pattern<DesignNoti> pattern = noFactory.create(designNoti).setEmpty(designNoti.getId()!=null);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			return this.designNotiServiceExd.save(designNoti,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public void finish(Long designNotiId, String userKey) {
		DesignNoti designNoti = this.designNotiRepository.findOne(designNotiId);
		
		User leader=new User();
		leader.setKey(userKey);
		designNoti.setSigner(leader);
		designNoti.setSigndate(new Date());
		designNoti.setState(DesignNotiState.finish);
		
		Pattern<DesignNoti> pattern = this.businoFactory.create(designNoti).setEmpty(StringUtils.isNotBlank(designNoti.getBusino()));
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			this.designNotiServiceExd.finish(designNoti,pattern);
		}finally{
			lock.unlock();
		}
		
	}

	@Override
	public List<DesignNoti> findByProject(Project project, DesignNotiState state) {
		return this.designNotiRepository.findByProjectAndStateOrderByIdDesc(project, state);
	}


	@Override
	@Transactional
	public void delete(Long designNotiId) {
		this.designNotiRepository.delete(designNotiId);
	}

	@Override
	public Page<DesignNoti> findAll(Specifications<DesignNoti> spec,
			Pageable pageable) {
		return this.designNotiRepository.findAll(spec, pageable);
	}

}
