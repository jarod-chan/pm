package cn.fyg.pm.domain.model.purchase.purchasereq.item;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseReqItemRepository extends CrudRepository<PurchaseReqItem, Long> {
	
	List<PurchaseReqItem> findByUptypeAndUpid(UptypeEnum uptype,Long upid);

}
