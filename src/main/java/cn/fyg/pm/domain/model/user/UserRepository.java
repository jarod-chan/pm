package cn.fyg.pm.domain.model.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface UserRepository extends JpaSpecificationExecutor<User>,Repository<User, String>{
	
	User save(User user); 
	
	void delete(String key);
	
	List<User> findAll();

	User findOne(String key);

	User findByKey(String username);

	List<User> findByEnabled(EnabledEnum enabled);
}
