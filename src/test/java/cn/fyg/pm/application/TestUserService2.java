package cn.fyg.pm.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.fyg.pm.domain2.user.User;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestUserService2 {
	
	@Autowired
	UserService2 userService2;
	
	@Test
	public void testSave(){
		User user = new User();
		user.setKey("test4");
		user.setName("realname4");
		this.userService2.save(user);
		
		this.userService2.delete(user.getKey());
	}

}
