package cn.fyg.pm.interfaces.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.service.UserService;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.message.Message;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;


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
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, Message.info("%s 设置为当前用户的",user.getName()));
		return "redirect:list";
	}

}
