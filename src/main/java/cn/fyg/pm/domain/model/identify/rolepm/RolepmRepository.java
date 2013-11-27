package cn.fyg.pm.domain.model.identify.rolepm;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.identify.role.Fnrole;

public interface RolepmRepository extends CrudRepository<Rolepm, Long> {
	
	List<Rolepm> findByFnrole(Fnrole fnrole);

}
