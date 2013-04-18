package cn.fyg.pm.domain2.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.fyg.pm.domain2.user.User;
import cn.fyg.pm.infrastructure.persistence.UserMapper;

@Repository
public class UserDao {
	
	@Autowired
	UserMapper userMapper;
	
	public List<User> findAll(){
		return userMapper.findAll();
	}
	
	public void save(User user){
		userMapper.insert(user);
	}
	
	public User find(String key){
		return userMapper.find(key);
	}
	
	public void delete(String key){
		userMapper.delete(key);
	}
	
	
}
