package cn.fyg.pm.domain.model.purchase.purchasecert;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface PurchaseCertRepository extends CrudRepository<PurchaseCert,Long>,RepositoryQuery<PurchaseCert> {

}
