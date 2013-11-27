package cn.fyg.pm.domain.model.purchase.purchasecert;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseCertRepository extends CrudRepository<PurchaseCert,Long>,JpaSpecificationExecutor<PurchaseCert> {

}
