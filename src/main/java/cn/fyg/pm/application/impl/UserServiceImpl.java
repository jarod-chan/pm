package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.user.User;
import cn.fyg.pm.domain.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
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

}
