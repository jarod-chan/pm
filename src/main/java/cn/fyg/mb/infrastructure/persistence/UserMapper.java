package cn.fyg.mb.infrastructure.persistence;

import java.util.List;

import cn.fyg.mb.domain.user.User;

public interface UserMapper {
	
	void insert(User user);
	
	 List<User> findAll();
	
	 User find(String key);
	
	 void delete(String key);

}
