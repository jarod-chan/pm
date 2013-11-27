package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.user.User;

public interface IdentifyService {
	
	List<String> findUserRole(User user);

	List<String> findUserPermission(User user);
}
