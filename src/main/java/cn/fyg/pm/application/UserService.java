package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.user.EnabledEnum;
import cn.fyg.pm.domain.model.user.User;

public interface UserService extends ServiceQuery<User> {
	
	User create();
	
	User save(User user);
	
	void delete(String key_);

	User find(String key);


	List<User> findAll();

	String login(String username, String password);
	
	boolean exist(String key);

	List<User> findByEnabled(EnabledEnum enabled);

}
