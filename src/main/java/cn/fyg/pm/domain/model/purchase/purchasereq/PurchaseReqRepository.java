package cn.fyg.pm.domain.model.purchase.purchasereq;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface PurchaseReqRepository extends CrudRepository<PurchaseReq,Long>,RepositoryQuery<PurchaseReq> {

}
