package cn.fyg.pm.application;

import cn.fyg.pm.domain2.user.User;

public interface UserService2 {
	
	User save(User user);
	
	void delete(String key_);

}
