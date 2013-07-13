package cn.fyg.pm.interfaces.web.module.user;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.user.EnabledEnum;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("user")
public class UserCtl {

	private static final String PATH="user/";
	private interface Page{
		String LIST=PATH+"list";
		String EDIT=PATH+"edit";
	}
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<User> users = this.userService.findAll();
		map.put("users", users);
		return Page.LIST;
	}
	
	@RequestMapping(value="{userKey}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("userKey") String userKey,Map<String,Object> map){
		User user = !userKey.equals("-1")?this.userService.find(userKey):createUser();
		map.put("user", user);
		map.put("enableds", EnabledEnum.values());
		return Page.EDIT;
	}
	
	private User createUser(){
		User user=new User();
		user.setPassword("0");
		user.setEnabled(EnabledEnum.y);
		return user;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("key")String userKey,HttpServletRequest request,RedirectAttributes redirectAttributes){		
		User user = this.userService.exist(userKey)?this.userService.find(userKey):createUser();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(user);
		binder.bind(request);
		this.userService.save(user);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}
	
}
