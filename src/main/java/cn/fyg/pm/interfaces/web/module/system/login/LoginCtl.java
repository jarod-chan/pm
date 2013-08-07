package cn.fyg.pm.interfaces.web.module.system.login;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.SpmemberService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.EnabledEnum;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;



@Controller
@RequestMapping("/login")
public class LoginCtl {
	
public static final Logger logger = LoggerFactory.getLogger(LoginCtl.class);
	
	private static final String PATH = "system/login/";
	private interface Page {
		String LOGIN = PATH + "login";
	}
	
	@Autowired
	UserService userService;
	@Autowired
	SpmemberService spmemberService;
	@Autowired
	SessionUtil sessionUtil;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String toLogin(Map<String,Object> map) {
		List<User> users = this.userService.findByEnabled(EnabledEnum.y);
		map.put("users", users);
		return Page.LOGIN;
	}
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String login(LoginBean loginBean,RedirectAttributes redirectAttributes) {
		String userKey=userService.login(loginBean.getUsername(), loginBean.getPassword());
		if(userKey==null){
			logger.error(String.format("key:[%s] password:[%s] login fail", loginBean.getUsername(),loginBean.getPassword()));	
			redirectAttributes.addFlashAttribute("loginBean", loginBean);
			redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("用户名或者密码错误！"));
			return "redirect:/login";
		}
		User user = userService.find(userKey);
		this.sessionUtil.setValue("user", user);
		if(isSupplierUser(user)){
			Supplier supplier=spmemberService.getUserSupplier(user);
			sessionUtil.setValue("supplier", supplier);
			return "redirect:/fm/contractor/task";
		}
		return "redirect:/fm/company/task";
	}
	
	//判断用户是否承包人
	private boolean isSupplierUser(User user) {
		return this.spmemberService.isUserAssigned(user);
	}



}
