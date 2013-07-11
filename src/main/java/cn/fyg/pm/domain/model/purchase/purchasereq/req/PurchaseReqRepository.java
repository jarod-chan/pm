package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface PurchaseReqRepository extends CrudRepository<PurchaseReq,Long>,RepositoryQuery<PurchaseReq> {
	
	List<PurchaseReq> findByPurchaseKey_Project(Project project);

	PurchaseReq findByPurchaseKey(PurchaseKey purchaseKey);

}
