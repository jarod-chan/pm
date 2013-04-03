package cn.fyg.mb.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.mb.application.UserService;
import cn.fyg.mb.domain.user.User;
import cn.fyg.mb.domain.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public User save(User user) {
		this.userRepository.save(user);
		return userRepository.find(user.getKey());
	}

	@Override
	@Transactional
	public void delete(String key) {
		this.userRepository.delete(key);
	}

}
