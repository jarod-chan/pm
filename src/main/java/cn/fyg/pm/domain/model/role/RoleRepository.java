package cn.fyg.pm.domain.model.role;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	//TODO  spring data jpa 无法直接生成
	@Query("select pj from Role pj order by pj.sn asc ") 
	List<Role> findAllOrderBySnAsc();
	
	List<Role> findByRoleTypeOrderBySnAsc(RoleType roleType);


}
