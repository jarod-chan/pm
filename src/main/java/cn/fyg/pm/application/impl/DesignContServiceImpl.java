package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.application.common.impl.SericeQueryRefImpl;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContCommitVld;
import cn.fyg.pm.domain.model.design.designcont.DesignContFactory;
import cn.fyg.pm.domain.model.design.designcont.DesignContRepository;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.design.designcont.sendlog.SendLog;
import cn.fyg.pm.domain.model.design.designcont.sendlog.SendLogRepository;
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

@Service("designContService")
public class DesignContServiceImpl extends SericeQueryRefImpl<DesignCont> implements DesignContService {
	
	@Autowired
	DesignContRepository designContRepository;
	@Autowired
	PjmemberRepository pjmemberRepository;
	@Autowired
	SendLogRepository sendLogRepository;
	@Autowired
	LockService lockService;
	@Autowired
	DesignContServiceExd designContServiceExd;
	@Autowired
	@Qualifier("designContNo")
	PatternFactory<DesignCont> noFactory;
	
	@Override
	public JpaSpecificationExecutor<DesignCont> getSpecExecutor() {
		return this.designContRepository;
	}

	@Autowired
	@Qualifier("designContBusino")
	PatternFactory<DesignCont> businoFactory;


	@Override
	public Result verifyForCommit(DesignCont designCont) {
		DesignContCommitVld vld = new DesignContCommitVld();
		vld.setValObject(designCont);
		return vld.verify();
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
	public DesignCont save(DesignCont designCont) {
		Pattern<DesignCont> pattern = noFactory.create(designCont).setEmpty(designCont.getId()!=null);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			return this.designContServiceExd.save(designCont,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public DesignCont finish(Long designContId, String userKey) {
		DesignCont designCont = this.designContRepository.findOne(designContId);
		User leader=new User();
		leader.setKey(userKey);
		designCont.setSigner(leader);
		designCont.setSigndate(new Date());
		designCont.setState(DesignContState.finish);
		
		Pattern<DesignCont> pattern = this.businoFactory.create(designCont).setEmpty(StringUtils.isNotBlank(designCont.getBusino()));
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			return this.designContServiceExd.finish(designCont,pattern);
		}finally{
			lock.unlock();
		}
		
	}

	@Override
	@Transactional
	public void delete(Long designContId) {
		this.designContRepository.delete(designContId);
	}

	@Override
	@Transactional
	public void sendLog(Long designContId, String receiver, Long sendnumb) {
		DesignCont designCont = this.designContRepository.findOne(designContId);
		designCont.setSendnumb(sendnumb);
		this.designContRepository.save(designCont);
		SendLog sendLog = new SendLog();
		sendLog.setDesigncont_id(designContId);
		sendLog.setReceiver(receiver);
		sendLog.setNumb(sendnumb);
		sendLog.setDate(new Date());
		this.sendLogRepository.save(sendLog);
	}

}
