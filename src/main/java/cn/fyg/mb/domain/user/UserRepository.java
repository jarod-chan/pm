package cn.fyg.mb.domain.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.fyg.mb.infrastructure.persistence.UserMapper;

@Repository
public class UserRepository {
	
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
