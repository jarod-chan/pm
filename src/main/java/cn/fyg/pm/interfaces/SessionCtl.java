package cn.fyg.pm.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.user.User;
import cn.fyg.pm.interfaces.web.module.shared.constant.Constant;
import cn.fyg.pm.interfaces.web.module.shared.message.Message;
import cn.fyg.pm.interfaces.web.module.shared.session.SessionUtil;


@Controller
public class SessionCtl {
	
	@Autowired
	UserService userService;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<User> userList = userService.findAll();
		map.put("userList", userList);
		return "list";
	}
	
	@RequestMapping(value="/session",method=RequestMethod.POST)
	public String setSession(@RequestParam("userKey")String userKey,RedirectAttributes redirectAttributes){
		sessionUtil.invalidate();
		User user = userService.find(userKey);
		sessionUtil.setValue("user", user);
		redirectAttributes.addFlashAttribute(Constant.MESSAGE_NAME, Message.info("%s 设置为当前用户的",user.getName()));
		return "redirect:list";
	}

}