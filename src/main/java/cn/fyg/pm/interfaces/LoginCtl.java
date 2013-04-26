package cn.fyg.pm.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
public class LoginCtl {
	
	@Autowired
	UserService userService;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String toLogin(Map<String,Object> map){
		List<User> userList = userService.findAll();
		map.put("userList", userList);
		return "login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("userKey")String userKey){
		User user = userService.find(userKey);
		sessionUtil.setValue("user", user);
		return "redirect:/first";
	}

}
