package cn.fyg.pm.domain.user;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String>{
	
	User save(User user); 
	
	void delete(String key);
	
}
