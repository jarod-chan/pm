package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;
import cn.fyg.pm.domain.model.nogenerator.look.Lock;
import cn.fyg.pm.domain.model.nogenerator.look.LockService;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertCommitVld;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertFactory;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertRepository;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqBusi;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.PurchaseReqItemRepository;
import cn.fyg.pm.domain.model.purchase.purchasereq.item.UptypeEnum;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.domain.shared.verify.Result;

@Service("purchaseCertService")
public class PurchaseCertServiceImpl implements PurchaseCertService {

	@Autowired
	PurchaseCertRepository purchaseCertRepository;
	@Autowired
	PjmemberRepository pjmemberRepository;
	@Autowired
	PurchaseReqItemRepository purchaseReqItemRepository;
	@Autowired
	PurchaseReqBusi purchaseReqBusi;
	@Autowired
	LockService lockService;
	@Autowired
	PurchaseCertServiceExd purchaseCertServiceExd;
	@Autowired
	@Qualifier("purchaseCertNo")
	PatternFactory<PurchaseCert> noFactroy;
	@Autowired
	@Qualifier("purchaseCertBusino")
	PatternFactory<PurchaseCert> businoFactroy;
	
	@Override
	public List<PurchaseCert> query(QuerySpec<PurchaseCert> querySpec) {
		return purchaseCertRepository.query(PurchaseCert.class, querySpec);
	}

	@Override
	public PurchaseCert find(Long purchaseCertId) {
		return purchaseCertRepository.findOne(purchaseCertId);
	}

	@Override
	public PurchaseCert create(User creater, Project project,PurchaseCertState state) {
		Role pjrole = new Role();
		pjrole.setKey("xmfzr");//TODO 固定取项目负责人角色
		List<Pjmember> pjmembers = this.pjmemberRepository.findByProjectAndRole(project, pjrole);
		User xmfzr = pjmembers.get(0).getUser();
		return PurchaseCertFactory.create(creater,xmfzr, project, state);
	}

	@Override
	public PurchaseCert save(PurchaseCert purchaseCert) {
		Pattern<PurchaseCert> pattern = this.noFactroy.create(purchaseCert).setEmpty(purchaseCert.getId()!=null);
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			return this.purchaseCertServiceExd.save(purchaseCert,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	@Transactional
	public void delete(Long purchaseCertId) {
		this.purchaseCertRepository.delete(purchaseCertId);
	}

	@Override
	@Transactional
	public void finish(Long purchaseCertId,String userKey){
		PurchaseCert purchaseCert = this.purchaseCertRepository.findOne(purchaseCertId);
		User leader=new User();
		leader.setKey(userKey);
		purchaseCert.setSigner(leader);
		purchaseCert.setSigndate(new Date());
		purchaseCert.setState(PurchaseCertState.finish);
		
		Pattern<PurchaseCert> pattern = this.businoFactroy.create(purchaseCert).setEmpty(StringUtils.isNotBlank(purchaseCert.getBusino()));
		Lock lock = this.lockService.getLock(pattern);
		lock.lock();
		try{
			this.purchaseCertServiceExd.finish(purchaseCert,pattern);
		}finally{
			lock.unlock();
		}
	}

	@Override
	public Result verifyForCommit(PurchaseCert purchaseCert) {
		List<PurchaseReqItem> purchaseReqItems= this.purchaseReqItemRepository.findByUptypeAndUpid(UptypeEnum.pm_purchasecert,purchaseCert.getId()); 
		PurchaseCertCommitVld vld = new PurchaseCertCommitVld();
		vld.setValObject(purchaseCert);
		vld.setPurchaseReqItems(purchaseReqItems);
		return vld.verify();
	}

	@Override
	@Transactional
	public void invalid(Long purchaseCertId) {
		this.purchaseReqBusi.rmReqItemList(UptypeEnum.pm_purchasecert, purchaseCertId);
		PurchaseCert purchaseCert = this.purchaseCertRepository.findOne(purchaseCertId);
		purchaseCert.setState(PurchaseCertState.invalid);
		this.purchaseCertRepository.save(purchaseCert);
	}

}
