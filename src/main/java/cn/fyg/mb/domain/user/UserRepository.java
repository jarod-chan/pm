package cn.fyg.mb.domain.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepository {
	
	public List<HashMap<String,Object>> getAll();
	

	public void save(User user);
	
	public User find(String key_);
	
	public void delete(String key_);
}
