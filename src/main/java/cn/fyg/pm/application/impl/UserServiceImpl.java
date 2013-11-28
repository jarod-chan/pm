package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.application.common.impl.SericeQueryImpl;
import cn.fyg.pm.domain.model.user.EnabledEnum;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.user.UserFactory;
import cn.fyg.pm.domain.model.user.UserRepository;

@Service("userService")
public class UserServiceImpl extends SericeQueryImpl<User> implements UserService {


	@Autowired
	UserRepository userRepository;
	
	@Override
	public JpaSpecificationExecutor<User> getSpecExecutor() {
		return this.userRepository;
	}

	
	@Override
	@Transactional
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(String key) {
		this.userRepository.delete(key);
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public User find(String key) {
		return this.userRepository.findOne(key);
	}

	@Override
	//TODO 重构登录方法
	public String login(String username, String password) {
		User user=this.userRepository.findByKey(username);
		if(user==null) return null;
		if(user.getPassword()==null||!user.getPassword().equals(password)){
			return null;
		}
		return user.getKey();
	}

	@Override
	public boolean exist(String key) {
		User user=this.userRepository.findByKey(key);
		return user!=null;
	}

	@Override
	public List<User> findByEnabled(EnabledEnum enabled) {
		return this.userRepository.findByEnabled(enabled);
	}

	@Override
	public Page<User> findAll(Specification<User> spec, Pageable pageable) {
		return this.userRepository.findAll(spec, pageable);
	}

	@Override
	public User create() {
		return UserFactory.create();
	}

}
