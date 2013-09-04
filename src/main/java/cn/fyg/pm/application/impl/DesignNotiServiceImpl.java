package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.DesignNotiService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContPU;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiFactory;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiItem;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiPU;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiRepository;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
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
	NoGeneratorBusi noGeneratorBusi;
	
	@Override
	public List<DesignNoti> query(QuerySpec<DesignNoti> querySpec) {
		return this.designNotiRepository.query(DesignNoti.class, querySpec);
	}

	@Override
	public Result verifyForCommit(DesignNoti t) {
		// TODO Auto-generated method stub
		return null;
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
	@Transactional
	public DesignNoti save(DesignNoti designNoti) {
		if(designNoti.getId()==null){
			noGeneratorBusi.generateNextNo(designNoti);
		}
		for(DesignNotiItem designNotiItem:designNoti.getDesignNotiItems()){
			designNotiItem.setDesignNoti(designNoti);
		}
		return this.designNotiRepository.save(designNoti);
	}

	@Override
	@Transactional
	public DesignNoti finish(Long designNotiId, String userKey) {
		DesignNoti designNoti = this.designNotiRepository.findOne(designNotiId);
		
		User leader=new User();
		leader.setKey(userKey);
		designNoti.setSigner(leader);
		designNoti.setSigndate(new Date());
		designNoti.setState(DesignNotiState.finish);
		
		if(StringUtils.isBlank(designNoti.getBusino())){			
			NoPatternUnit pu = new DesignNotiPU(designNoti);
			this.noGeneratorBusi.generateNextNo(pu);
		}
		
		return this.designNotiRepository.save(designNoti);
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

}
