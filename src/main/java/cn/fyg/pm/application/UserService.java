package cn.fyg.pm.application;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.user.EnabledEnum;
import cn.fyg.pm.domain.model.user.User;

public interface UserService {
	
	User create();
	
	User save(User user);
	
	void delete(String key_);

	List<User> findAll();
	
	User find(String key);

	String login(String username, String password);
	
	boolean exist(String key);

	List<User> findByEnabled(EnabledEnum enabled);

	Page<User> findAll(Specification<User> spec, Pageable pageable);
}
