package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContCommitVld;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContFactory;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContPU;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContRepository;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.pjrole.Pjrole;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.domain.shared.verify.Result;

@Service("constructContService")
public class ConstructContServiceImpl implements ConstructContService {
	
	@Autowired
	ConstructContRepository constructContRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;
	@Autowired
	PjmemberRepository pjmemberRepository;

	@Override
	public List<ConstructCont> findAll() {
		return this.constructContRepository.findAll();
	}

	@Override
	@Transactional
	public ConstructCont save(ConstructCont constructCont) {
		if(constructCont.getId()==null){
			this.noGeneratorBusi.generateNextNo(constructCont);
		}
		//TODO 待重构
		for(ConstructContItem constructContItem:constructCont.getConstructContItems()){
			constructContItem.setConstructCont(constructCont);
		}
		return this.constructContRepository.save(constructCont);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.constructContRepository.delete(id);
	}

	@Override
	public ConstructCont find(Long id) {
		return this.constructContRepository.findOne(id);
	}

	@Override
	public ConstructCont findByConstructKey(ConstructKey constructKey) {
		return this.constructContRepository.findByConstructKey(constructKey);
	}

	@Override
	public ConstructCont create(User creater,Project project, ConstructContState state) {
		Pjrole pjrole = new Pjrole();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndPjrole(project, pjrole);
		if(pjmembers==null||pjmembers.isEmpty()){
			throw new RuntimeException("项目未设置项目负责人");
		}
		User xmfzr = pjmembers.get(0).getUser();
		return ConstructContFactory.create(creater,xmfzr,project,state);
	}

	@Override
	public List<ConstructCont> constructContCanBeSelected(Project project,ConstructContState state,Long constructCertId) {
		return this.constructContRepository.findConstructContCanBeSelected(project,state,constructCertId);
	}

	@Override
	public List<ConstructCont> query(QuerySpec<ConstructCont> querySpec) {
		return this.constructContRepository.query(ConstructCont.class,querySpec);
	}

	@Override
	public List<ConstructCont> findConstructContCanBeSelectedSupplier(Project project,Long constructCertId,Supplier supplier) {
		return this.constructContRepository.findConstructContCanBeSelectedSupplier(project,ConstructContState.finish,constructCertId,supplier);
	}

	@Override
	@Transactional
	public void finish(Long constructContId,String userKey) {
		ConstructCont constructCont = this.constructContRepository.findOne(constructContId);
		
		User leader=new User();
		leader.setKey(userKey);
		constructCont.setSigner(leader);
		constructCont.setSigndate(new Date());
		constructCont.setState(ConstructContState.finish);
		
		if(StringUtils.isBlank(constructCont.getBusino())){			
			NoPatternUnit pu = new ConstructContPU(constructCont);
			this.noGeneratorBusi.generateNextNo(pu);
		}
		this.constructContRepository.save(constructCont);
	}

	@Override
	public Result verifyForCommit(ConstructCont constructCont) {
		ConstructContCommitVld vld = new ConstructContCommitVld();
		vld.setValObject(constructCont);
		return vld.verify();
	}

	@Override
	@Transactional
	public void invalid(Long constructContId) {
		ConstructCont constructCont = this.constructContRepository.findOne(constructContId);
		constructCont.setState(ConstructContState.invalid);
		this.constructContRepository.save(constructCont);
	}

}
