package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PurchaseReqService;
import cn.fyg.pm.application.common.impl.SericeQueryImpl;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqBusi;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqCommitVld;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqFactory;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqRepository;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.verify.Result;

@Service("purchaseReqService")
public class PurchaseReqServiceImpl extends SericeQueryImpl<PurchaseReq> implements PurchaseReqService {
	
	@Autowired
	PurchaseReqRepository purchaseReqRepository;
	@Autowired
	PurchaseReqBusi purchaseReqBusi;
	@Autowired
	PjmemberRepository pjmemberRepository;
	@Autowired
	LockService lockService;
	@Autowired
	@Qualifier("purchaseReqNo")
	PatternFactory<PurchaseReq> noFactory;
	@Autowired
	@Qualifier("purchaseReqBusino")
	PatternFactory<PurchaseReq> businoFactory;
	@Autowired
	PurchaseReqServiceExd purchaseReqServiceExd;


	@Override
	public JpaSpecificationExecutor<PurchaseReq> getSpecExecutor() {
		return this.purchaseReqRepository;
	}

	@Override
	public PurchaseReq find(Long purchaseReqId) {
		return purchaseReqRepository.findOne(purchaseReqId);
	}

	@Override
	public PurchaseReq create(User creater, Project project,PurchaseReqState state) {
		Role pjrole = new Role();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndRole(project, pjrole);
		User xmfzr = pjmembers.get(0).getUser();
		return PurchaseReqFactory.create(creater,xmfzr, project, state);
	}

	@Override
	@Transactional
	public PurchaseReq save(PurchaseReq purchaseReq) {
		Pattern<PurchaseReq> pattern = noFactory.create(purchaseReq).setEmpty(purchaseReq.getId()!=null);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			return this.purchaseReqServiceExd.save(purchaseReq,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	@Transactional
	public void delete(Long purchaseReqId) {
		this.purchaseReqRepository.delete(purchaseReqId);
	}

	@Override
	public List<PurchaseReq> findByProject(Project project,PurchaseReqState state) {
		return this.purchaseReqRepository.findByPurchaseKey_ProjectAndStateOrderByIdDesc(project,state);
	}

	@Override
	public PurchaseReq findByPurchaseKey(PurchaseKey purchaseKey) {
		return this.purchaseReqRepository.findByPurchaseKey(purchaseKey);
	}

	@Override
	@Transactional
	public void upReqItemList(UptypeEnum uptype, Long upid, String upno,Long[] upReqItemIds) {
		purchaseReqBusi.upReqItemList(uptype, upid, upno, upReqItemIds);
	}

	@Override
	@Transactional
	public void rmReqItemList(UptypeEnum uptype, Long upid) {
		purchaseReqBusi.rmReqItemList(uptype, upid);
	}

	@Override
	public void finish(Long purchaseReqId,String userKey){
		PurchaseReq purchaseReq = this.purchaseReqRepository.findOne(purchaseReqId);
		User leader=new User();
		leader.setKey(userKey);
		purchaseReq.setSigner(leader);
		purchaseReq.setSigndate(new Date());
		purchaseReq.setState(PurchaseReqState.finish);
		
		Pattern<PurchaseReq> pattern = this.businoFactory.create(purchaseReq).setEmpty(StringUtils.isNotBlank(purchaseReq.getBusino()));
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			this.purchaseReqServiceExd.finish(purchaseReq,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public Result verifyForCommit(PurchaseReq purchaseReq) {
		PurchaseReqCommitVld vld=new PurchaseReqCommitVld();
		vld.setValObject(purchaseReq);
		return vld.verify();
	}

}
