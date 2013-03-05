package cn.fyg.mb.application;

import cn.fyg.mb.domain.user.User;

public interface UserService {
	
	User save(User user);
	
	void delete(String key_);

}
