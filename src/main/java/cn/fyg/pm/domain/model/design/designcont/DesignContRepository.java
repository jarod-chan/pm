package cn.fyg.pm.domain.model.design.designcont;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DesignContRepository extends CrudRepository<DesignCont,Long>,JpaSpecificationExecutor<DesignCont> {

}
