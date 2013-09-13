package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.RoleService;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.role.RoleRepository;
import cn.fyg.pm.domain.model.role.RoleType;

@Service("roleService")
public class PjroleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<Role> findByRoleType(RoleType roleType) {
		return this.roleRepository.findByRoleTypeOrderBySnAsc(roleType);
	}

}
