package cn.fyg.pm.application;

import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;

public interface SymemberService {
	
	void setUserRole(User user,Role role);

	Role findByUser(User user);

	void removeUserRole(User user);
	
	String roleUser(String roleKey);

}
