package cn.fyg.pm.domain.model.user;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String>{
	
	User save(User user); 
	
	void delete(String key);
	
	List<User> findAll();

	User findOne(String key);
}
