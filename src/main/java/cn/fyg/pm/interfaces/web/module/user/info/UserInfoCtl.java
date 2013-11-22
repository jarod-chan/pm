package cn.fyg.pm.interfaces.web.module.user.info;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.SymemberService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.infrastructure.tool.encrypt.Encipher;
import cn.fyg.pm.infrastructure.tool.encrypt.SaltGenerator;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;

@Controller
@RequestMapping("user")
public class UserInfoCtl {
	
	
	private static final String PATH="user/";

	private interface Page {
		String INFO = PATH + "info";
	}
	
	@Autowired
	UserService userService;
	@Autowired
	SymemberService symemberService;
	@Autowired
	Encipher encipher;
	@Autowired
	SaltGenerator saltGenerator;
	
	@RequestMapping(value="{key}/info",method=RequestMethod.GET)
	public String toList(@PathVariable("key")String key,Map<String,Object> map){
		User user= this.userService.find(key);
		map.put("userInfo", user);
		Role userRole=this.symemberService.findByUser(user);
		map.put("userRole", userRole);
		return Page.INFO;
	}
	
	@RequestMapping(value="{key}/info/save",method=RequestMethod.POST)
	public String save(@PathVariable("key")String key,@RequestParam("set-password")String setPassword,HttpServletRequest request,RedirectAttributes redirectAttributes){
		User user= this.userService.find(key);
		BindTool.bindRequest(user, request);
		//如果输入重置密码，则生成新密码
		if(StringUtils.isNotBlank(setPassword)){
			String salt=this.saltGenerator.getSalt();
			String password=this.encipher.encrypt(setPassword, salt);
			user.setSalt(salt);
			user.setPassword(password);
		}
		this.userService.save(user);
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:/user/{key}/info";
	}

}
