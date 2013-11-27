package cn.fyg.pm.interfaces.web.module.user.manage;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
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

import cn.fyg.pm.application.RoleService;
import cn.fyg.pm.application.SymemberService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.role.RoleType;
import cn.fyg.pm.domain.model.user.EnabledEnum;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.infrastructure.tool.encrypt.Encipher;
import cn.fyg.pm.infrastructure.tool.encrypt.SaltGenerator;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.mvc.BindTool;

@Controller
@RequestMapping("user")
public class UserCtl {

	private static final String PATH="user/";

	private interface Page {
		String LIST = PATH + "list";
		String NEW = PATH + "new";
		String EDIT = PATH + "edit";
	}

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	SymemberService symemberService;
	@Autowired
	SaltGenerator saltGenerator;
	@Autowired
	Encipher encipher;
	
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList(Map<String,Object> map){
		List<User> users = this.userService.findAll();
		map.put("users", users);
		return Page.LIST;
	}
	
	@RequestMapping(value="new",method=RequestMethod.GET)
	public String toNew(Map<String,Object> map){
		User user = this.userService.create();
		map.put("user", user);
		renderPage(map);
		return Page.NEW;
	}

	@RequestMapping(value="{userKey}/edit",method=RequestMethod.GET)
	public String toEdit(@PathVariable("userKey") String userKey,Map<String,Object> map){
		User user = this.userService.find(userKey);
		map.put("user", user);
		Role userRole=this.symemberService.findByUser(user);
		map.put("userRole", userRole);
		
		renderPage(map);
		return Page.EDIT;
	}

	public void renderPage(Map<String, Object> map) {
		map.put("enableds", EnabledEnum.values());
		List<Role> roles = this.roleService.findByRoleType(RoleType.system);
		map.put("roles", roles);
	}

	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("key")String userKey,@RequestParam("roleKey")String roleKey,@RequestParam("set-password")String setPassword,HttpServletRequest request,RedirectAttributes redirectAttributes){		
		User user = this.userService.exist(userKey)?this.userService.find(userKey):this.userService.create();
		
		BindTool.bindRequest(user, request);
		//如果输入重置密码，则生成新密码
		if(StringUtils.isNotBlank(setPassword)){
			String salt=this.saltGenerator.getSalt();
			String password=this.encipher.encrypt(setPassword, salt);
			user.setSalt(salt);
			user.setPassword(password);
		}
		this.userService.save(user);
		if(StringUtils.isNotBlank(roleKey)){
			Role userRole=new Role();
			userRole.setKey(roleKey);
			this.symemberService.setUserRole(user, userRole);
		}else{
			this.symemberService.removeUserRole(user);
		}
		redirectAttributes.addFlashAttribute(AppConstant.MESSAGE_NAME, info("保存成功"));
		return "redirect:list";
	}
	
}
