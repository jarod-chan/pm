package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;

public interface PurchaseReqRepository extends CrudRepository<PurchaseReq,Long>,JpaSpecificationExecutor<PurchaseReq>{
	
	List<PurchaseReq> findByPurchaseKey_ProjectAndStateOrderByIdDesc(Project project,PurchaseReqState state);

	PurchaseReq findByPurchaseKey(PurchaseKey purchaseKey);

}
