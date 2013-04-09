package cn.fyg.pm.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.user.User;

@Controller
public class HelloCtl {

	private static final String PATH = "";
	private interface Page {
		String HELLO = PATH + "hello";
	}
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String toHello(Map<String,Object> map){
		List<User> userList = userService.findAll();
		map.put("userList", userList);
		return Page.HELLO;
	}

}
