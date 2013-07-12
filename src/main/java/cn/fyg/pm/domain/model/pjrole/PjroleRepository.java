package cn.fyg.pm.domain.model.pjrole;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PjroleRepository extends CrudRepository<Pjrole, Long> {
	
	//TODO  spring data jpa 无法直接生成
	@Query("select pj from Pjrole pj order by pj.sn asc ") 
	List<Pjrole> findAllOrderBySnAsc();


}
