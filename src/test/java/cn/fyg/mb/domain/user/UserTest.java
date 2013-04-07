package cn.fyg.mb.domain.user;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void test(){
		List<User> list=userRepository.findAll();
		System.out.println(list.size());
		for(User user:list){
			System.out.println(user);
		}
	}
	

	@Test
	public void testSave(){
		User user=new User();
		user.setKey("key3");
		user.setRealname("realname3");
		userRepository.save(user);
		
		user=userRepository.find("key3");
		assertNotNull(user);
		
		userRepository.delete("key3");
	}
	

}
