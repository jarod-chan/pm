package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.application.common.impl.SericeQueryImpl;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContCommitVld;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContFactory;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContRepository;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;

@Service("constructContService")
public class ConstructContServiceImpl extends SericeQueryImpl<ConstructCont> implements ConstructContService {
	
	@Autowired
	ConstructContRepository constructContRepository;
	@Autowired
	PjmemberRepository pjmemberRepository;
	@Autowired
	LockService lockService;
	@Autowired
	ConstructContServiceExd constructContServiceExd;
	@Autowired
	@Qualifier("constructContNo")
	PatternFactory<ConstructCont> noFactory;
	@Autowired
	@Qualifier("constructContBusino")
	PatternFactory<ConstructCont> businoFactory;
	
	@Override
	public JpaSpecificationExecutor<ConstructCont> getSpecExecutor() {
		return this.constructContRepository;
	}

	@Override
	public ConstructCont save(ConstructCont constructCont) {
		Pattern<ConstructCont> pattern = noFactory.create(constructCont).setEmpty(constructCont.getId()!=null);
		Lock lock = lockService.getLock(pattern);
		lock.lock();
		try{
			return this.constructContServiceExd.save(constructCont,pattern);
		}finally{
			lock.unlock();
		}
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
		Role pjrole = new Role();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndRole(project, pjrole);
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
	public List<ConstructCont> findConstructContCanBeSelectedSupplier(Project project,Long constructCertId,Supplier supplier) {
		return this.constructContRepository.findConstructContCanBeSelectedSupplier(project,ConstructContState.finish,constructCertId,supplier);
	}

	@Override
	public void finish(Long constructContId,String userKey) {
		ConstructCont constructCont = this.constructContRepository.findOne(constructContId);
		
		User leader=new User();
		leader.setKey(userKey);
		constructCont.setSigner(leader);
		constructCont.setSigndate(new Date());
		constructCont.setState(ConstructContState.finish);
		
		Pattern<ConstructCont> pattern = businoFactory.create(constructCont).setEmpty(StringUtils.isNotBlank(constructCont.getBusino()));
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			this.constructContServiceExd.finish(constructCont,pattern);
		}finally{
			lock.unlock();
		}

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

	@Override
	public Page<ConstructCont> findAll(Specification<ConstructCont> spec,
			Pageable pageable) {
		return this.constructContRepository.findAll(spec, pageable);
	}

}
