package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.application.common.impl.SericeQueryRefImpl;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertCommitVld;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertFactory;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertRepository;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKeyRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;

@Service("constructCertService")
public class ConstructCertServiceImpl extends SericeQueryRefImpl<ConstructCert> implements ConstructCertService {
	
	@Autowired
	ConstructCertRepository constructCertRepository;
	@Autowired
	PjmemberRepository pjmemberRepository;
	@Autowired
	ConstructKeyRepository constructKeyRepository;
	@Autowired
	ConstructCertServiceExd constructCertServiceExd;
	@Autowired
	LockService lockService;
	@Autowired
	@Qualifier("constructCertNo")
	PatternFactory<ConstructCert> noFactory;
	@Autowired
	@Qualifier("constructCertBusino")
	PatternFactory<ConstructCert> businoFactory;
	
	@Override
	public JpaSpecificationExecutor<ConstructCert> getSpecExecutor() {
		return this.constructCertRepository;
	}

	@Override
	public List<ConstructCert> findAll() {
		return constructCertRepository.findAll();
	}

	@Override
	@Transactional
	public ConstructCert save(ConstructCert constructCert) {
		Pattern<ConstructCert> pattern = noFactory.create(constructCert).setEmpty(constructCert.getId()!=null);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			return this.constructCertServiceExd.save(constructCert,pattern);
		}finally{
			lock.unlock();
		}
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.constructCertRepository.delete(id);
	}

	@Override
	public ConstructCert findByConstructKey(ConstructKey constructKey) {
		return this.constructCertRepository.findByConstructKey(constructKey);
	}

	@Override
	public ConstructCert find(Long certid) {
		return this.constructCertRepository.findOne(certid);
	}

	@Override
	public ConstructCert create(User user,Project project,ConstructCertState state) {
		Role pjrole = new Role();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndRole(project, pjrole);
		User xmfzr = pjmembers.get(0).getUser();
		return ConstructCertFactory.create(user,xmfzr,project,state);
	}

	@Override
	public List<ConstructCert> findByProject(Project project) {
		return this.constructCertRepository.findByConstructKey_Project(project);
	}


	@Override
	public void finish(Long constructCertId, String userKey) {
		ConstructCert constructCert = this.constructCertRepository.findOne(constructCertId);
		User leader=new User();
		leader.setKey(userKey);
		constructCert.setSigner(leader);
		constructCert.setSigndate(new Date());
		constructCert.setState(ConstructCertState.finish);
		
		Pattern<ConstructCert> pattern = businoFactory.create(constructCert).setEmpty(StringUtils.isNotBlank(constructCert.getBusino()));
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try {
			this.constructCertServiceExd.finish(constructCert,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public Result verifyForCommit(ConstructCert constructCert) {
		ConstructKey constructKey = this.constructKeyRepository.findOne(constructCert.getConstructKey().getId());
		constructCert.setConstructKey(constructKey);
		//TODO 检查是否被其它联系单关联
		ConstructCert conflictConstructCert=null;
		if(constructKey.getConstructcert_id()!=null&&!constructKey.getConstructcert_id().equals(constructCert.getId())){
			conflictConstructCert=this.constructCertRepository.findOne(constructKey.getConstructcert_id());
		}
		ConstructCertCommitVld val=new ConstructCertCommitVld();
		val.setValObject(constructCert);
		val.setConflictConstructCert(conflictConstructCert);
		Result result=val.verify();
		this.constructKeyRepository.save(constructCert.getConstructKey());
		return result;
	}

	@Override
	@Transactional
	public void Invalid(Long constructCertId) {
		ConstructCert constructCert = this.constructCertRepository.findOne(constructCertId);
		constructCert.setState(ConstructCertState.invalid);
		ConstructKey constructKey = constructCert.getConstructKey();
		constructKey.setConstructcert_id(null);
		this.constructKeyRepository.save(constructKey);
		this.constructCertRepository.save(constructCert);
	}

	




}
