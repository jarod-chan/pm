package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertFactory;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertPU;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertRepository;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.pjrole.Pjrole;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("constructCertService")
public class ConstructCertServiceImpl implements ConstructCertService {
	
	@Autowired
	ConstructCertRepository constructCertRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;
	@Autowired
	PjmemberRepository pjmemberRepository;

	@Override
	public List<ConstructCert> findAll() {
		return constructCertRepository.findAll();
	}

	@Override
	@Transactional
	public ConstructCert save(ConstructCert constructCert) {
		if(constructCert.getId()==null){
			noGeneratorBusi.generateNextNo(constructCert);
		}
		return constructCertRepository.save(constructCert);
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
		Pjrole pjrole = new Pjrole();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndPjrole(project, pjrole);
		User xmfzr = pjmembers.get(0).getUser();
		return ConstructCertFactory.create(user,xmfzr,project,state);
	}

	@Override
	public List<ConstructCert> findByProject(Project project) {
		return this.constructCertRepository.findByConstructKey_Project(project);
	}

	@Override
	public List<ConstructCert> query(QuerySpec<ConstructCert> querySpec) {
		return this.constructCertRepository.query(ConstructCert.class,querySpec);
	}


	@Override
	public void finish(Long constructCertId, String userKey) {
		ConstructCert constructCert = this.constructCertRepository.findOne(constructCertId);
		User leader=new User();
		leader.setKey(userKey);
		constructCert.setSigner(leader);
		constructCert.setSigndate(new Date());
		constructCert.setState(ConstructCertState.finish);
		
		if(constructCert.getBusino()==null){
			ConstructCertPU pu = new ConstructCertPU(constructCert);
			this.noGeneratorBusi.generateNextNo(pu);
		}
		this.save(constructCert);
	}


}
