package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
import cn.fyg.pm.domain.model.pjmember.Pjmember;
import cn.fyg.pm.domain.model.pjmember.PjmemberRepository;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertCommitVld;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertFactory;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertItem;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertPU;
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
	NoGeneratorBusi noGeneratorBusi;
	@Autowired
	PjmemberRepository pjmemberRepository;
	@Autowired
	PurchaseReqItemRepository purchaseReqItemRepository;
	@Autowired
	PurchaseReqBusi purchaseReqBusi;
	
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
	@Transactional
	public PurchaseCert save(PurchaseCert purchaseCert) {
		if(purchaseCert.getId()==null){
			noGeneratorBusi.generateNextNo(purchaseCert);
		}
		for (PurchaseCertItem purchaseCertItem : purchaseCert.getPurchaseCertItems()) {
			purchaseCertItem.setPurchaseCert(purchaseCert);
		}
		return purchaseCertRepository.save(purchaseCert);
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
		if(purchaseCert.getBusino()==null){
			NoPatternUnit pu = new PurchaseCertPU(purchaseCert);
			this.noGeneratorBusi.generateNextNo(pu);
		}
		this.purchaseCertRepository.save(purchaseCert);
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
