package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.user.User;

public interface UserService {
	
	User save(User user);
	
	void delete(String key_);

	List<User> findAll();
	
	User find(String key);

}
