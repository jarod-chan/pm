package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.UserService2;
import cn.fyg.pm.domain2.user.User;
import cn.fyg.pm.domain2.user.UserDao;

@Service
public class UserService2Impl implements UserService2 {

	@Autowired
	UserDao userDao;
	
	@Override
	@Transactional("transactionManager1")
	public User save(User user) {
		this.userDao.save(user);
		return userDao.find(user.getKey());
	}

	@Override
	@Transactional("transactionManager1")
	public void delete(String key) {
		this.userDao.delete(key);
	}

}
