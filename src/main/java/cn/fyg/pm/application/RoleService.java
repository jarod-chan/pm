package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.role.RoleType;

public interface RoleService {
	
	List<Role> findByRoleType(RoleType roleType);

}
