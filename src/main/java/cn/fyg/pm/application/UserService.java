package cn.fyg.pm.application;

import cn.fyg.pm.domain.user.User;

public interface UserService {
	
	User save(User user);
	
	void delete(String key_);

}
