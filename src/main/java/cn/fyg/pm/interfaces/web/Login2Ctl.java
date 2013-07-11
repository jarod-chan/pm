package cn.fyg.pm.interfaces.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;

@Controller
public class Login2Ctl {
	
	@Autowired
	UserService userService;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/login2",method=RequestMethod.GET)
	public String toLogin(Map<String,Object> map){
		List<User> userList = userService.findAll();
		map.put("userList", userList);
		return "login";
	}
	
	@RequestMapping(value="/login2",method=RequestMethod.POST)
	public String login(@RequestParam("userKey")String userKey){
		User user = userService.find(userKey);
		sessionUtil.setValue("user", user);
		return "redirect:/first";
	}

}
