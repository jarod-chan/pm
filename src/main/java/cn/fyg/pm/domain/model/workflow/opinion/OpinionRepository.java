package cn.fyg.pm.domain.model.workflow.opinion;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.fyg.pm.domain.shared.BusiCode;

public interface OpinionRepository extends CrudRepository<Opinion,Long> {
	
	List<Opinion> findByBusiCodeAndBusinessIdOrderByIdAsc(BusiCode busiCode,Long businessId);

	@Modifying 
	@Query("delete from Opinion a where a.busiCode=:busiCode and a.businessId=:businessId") 
	void deleteByBusiCodeAndBusinessId(@Param("busiCode")BusiCode busiCode,@Param("businessId")Long businessId);
}
