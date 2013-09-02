package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContFactory;
import cn.fyg.pm.domain.model.design.designcont.DesignContItem;
import cn.fyg.pm.domain.model.design.designcont.DesignContRepository;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.domain.shared.verify.Result;

@Service("designContService")
public class DesignContServiceImpl implements DesignContService {
	
	@Autowired
	DesignContRepository designContRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;
	@Autowired
	PjmemberRepository pjmemberRepository;

	@Override
	public List<DesignCont> query(QuerySpec<DesignCont> querySpec) {
		return this.designContRepository.query(DesignCont.class, querySpec);
	}

	@Override
	public Result verifyForCommit(DesignCont t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DesignCont find(Long designContId) {
		return this.designContRepository.findOne(designContId);
	}

	@Override
	public DesignCont create(User creater,Project project,DesignContState state) {
		Role pjrole = new Role();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndRole(project, pjrole);
		User xmfzr = pjmembers.get(0).getUser();
		return DesignContFactory.create(creater,xmfzr,project,state);
	}

	@Override
	@Transactional
	public DesignCont save(DesignCont designCont) {
		if(designCont.getId()==null){
			noGeneratorBusi.generateNextNo(designCont);
		}
		for(DesignContItem designContItem:designCont.getDesignContItems()){
			designContItem.setDesignCont(designCont);
		}
		return this.designContRepository.save(designCont);
	}

	@Override
	public DesignCont finish(Long designContId, String userKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DesignCont> findByProject(Project project, DesignContState state) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public DesignCont findByDesignContKey(DesignKey designKey) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	@Transactional
	public void delete(Long designContId) {
		this.designContRepository.delete(designContId);
	}

}
