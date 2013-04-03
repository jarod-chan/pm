package cn.fyg.mb.domain.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.fyg.mb.infrastructure.persistence.UserMapper;

@Repository
public class UserRepository {
	
	@Autowired
	UserMapper userMapper;
	
	public List<HashMap<String,Object>> getAll(){
		return userMapper.getAll();
	}
	

	public void save(User user){
		userMapper.save(user);
	}
	
	public User find(String key_){
		return userMapper.find(key_);
	}
	
	public void delete(String key_){
		userMapper.delete(key_);
	}
}
