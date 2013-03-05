package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.fyg.mb.domain.user.User;
import cn.fyg.mb.domain.user.UserRepository;
import cn.fyg.mb.infrastructure.persistence.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {
	
	@Autowired
	UserMapper userMapper;
	
	@Test
	public void test(){
		List<HashMap<String,Object>> list=userMapper.getAll();
		System.out.println(list.size());
		for(HashMap<String,Object> map:list){
			System.out.println(map.get("uuid"));
			System.out.println(map.get("realname"));
		}
	}
	

	
	@Test
	public void testSave(){
		User user=new User();
		user.setKey_("key3");
		user.setRealname("realname3");
		userMapper.save(user);
		
		user=userMapper.find("key3");
		assertNotNull(user);
		
		userMapper.delete("key3");
	}
	

}
