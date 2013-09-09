package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.DesignContService;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContCommitVld;
import cn.fyg.pm.domain.model.design.designcont.DesignContFactory;
import cn.fyg.pm.domain.model.design.designcont.DesignContItem;
import cn.fyg.pm.domain.model.design.designcont.DesignContPU;
import cn.fyg.pm.domain.model.design.designcont.DesignContRepository;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.design.designcont.sendlog.SendLog;
import cn.fyg.pm.domain.model.design.designcont.sendlog.SendLogRepository;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
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
	@Autowired
	SendLogRepository sendLogRepository;

	@Override
	public List<DesignCont> query(QuerySpec<DesignCont> querySpec) {
		return this.designContRepository.query(DesignCont.class, querySpec);
	}

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
	@Transactional
	public DesignCont finish(Long designContId, String userKey) {
		DesignCont designCont = this.designContRepository.findOne(designContId);
		User leader=new User();
		leader.setKey(userKey);
		designCont.setSigner(leader);
		designCont.setSigndate(new Date());
		designCont.setState(DesignContState.finish);
		if(StringUtils.isBlank(designCont.getBusino())){
			NoPatternUnit pu = new DesignContPU(designCont);
			this.noGeneratorBusi.generateNextNo(pu);
		}
		return this.designContRepository.save(designCont);
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
		sendLog.setReceiver(receiver);
		sendLog.setNumb(sendnumb);
		sendLog.setDate(new Date());
		this.sendLogRepository.save(sendLog);
	}

}
