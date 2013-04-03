package cn.fyg.mb.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;

import cn.fyg.mb.domain.user.User;

public interface UserMapper {
	
	public List<HashMap<String,Object>> getAll();
	
	public void save(User user);
	
	public User find(String key_);
	
	public void delete(String key_);

}
