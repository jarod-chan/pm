package cn.fyg.pm.infrastructure.persistence;

import java.util.List;

import cn.fyg.pm.domain2.user.User;

public interface UserMapper {
	
	void insert(User user);
	
	 List<User> findAll();
	
	 User find(String key);
	
	 void delete(String key);

}
