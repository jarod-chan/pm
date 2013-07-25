package cn.fyg.pm.domain.model.workflow.opinion;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.shared.BusiCode;

public interface OpinionRepository extends CrudRepository<Opinion,Long> {
	
	List<Opinion> findByBusiCodeAndBusinessIdOrderByIdAsc(BusiCode busiCode,Long businessId);

}
