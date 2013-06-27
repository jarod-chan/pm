package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PurchaseCertService;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertFactory;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertItem;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertRepository;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("purchaseCertService")
public class PurchaseCertServiceImpl implements PurchaseCertService {

	@Autowired
	PurchaseCertRepository purchaseCertRepository;
	
	@Override
	public List<PurchaseCert> query(QuerySpec<PurchaseCert> querySpec) {
		return purchaseCertRepository.query(PurchaseCert.class, querySpec);
	}

	@Override
	public PurchaseCert find(Long purchaseCertId) {
		return purchaseCertRepository.findOne(purchaseCertId);
	}

	@Override
	public PurchaseCert create(User creater, Project project,
			PurchaseCertState state, boolean generateNo) {
		return PurchaseCertFactory.create(creater, project, state, generateNo);
	}

	@Override
	@Transactional
	public PurchaseCert save(PurchaseCert purchaseCert) {
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

}
