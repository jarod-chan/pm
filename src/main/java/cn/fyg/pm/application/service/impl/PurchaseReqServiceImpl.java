package cn.fyg.pm.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.service.PurchaseReqService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqFactory;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqItem;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqRepository;
import cn.fyg.pm.domain.model.purchase.purchasereq.PurchaseReqState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("purchaseReqService")
public class PurchaseReqServiceImpl implements PurchaseReqService {
	
	@Autowired
	PurchaseReqRepository purchaseReqRepository;

	@Override
	public List<PurchaseReq> query(QuerySpec<PurchaseReq> querySpec) {
		return purchaseReqRepository.query(PurchaseReq.class, querySpec);
	}

	@Override
	public PurchaseReq find(Long purchaseReqId) {
		return purchaseReqRepository.findOne(purchaseReqId);
	}

	@Override
	public PurchaseReq create(User creater, Project project,
			PurchaseReqState state, boolean generateNo) {
		return PurchaseReqFactory.create(creater, project, state, generateNo);
	}

	@Override
	@Transactional
	public PurchaseReq save(PurchaseReq purchaseReq) {
		for(PurchaseReqItem purchaseReqItem:purchaseReq.getPurchaseReqItems()){
			purchaseReqItem.setPurchaseReq(purchaseReq);
		}
		return purchaseReqRepository.save(purchaseReq);
	}

	@Override
	@Transactional
	public void delete(Long purchaseReqId) {
		this.purchaseReqRepository.delete(purchaseReqId);
	}

	@Override
	public List<PurchaseReq> findByProject(Project project) {
		return this.purchaseReqRepository.findByPurchaseKey_Project(project);
	}

	@Override
	public PurchaseReq findByPurchaseKey(PurchaseKey purchaseKey) {
		return this.purchaseReqRepository.findByPurchaseKey(purchaseKey);
	}

}
