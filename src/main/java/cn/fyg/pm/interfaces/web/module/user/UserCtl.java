package cn.fyg.pm.interfaces.web.module.user;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
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
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;

@Controller
@RequestMapping("user")
public class UserCtl {

	private static final String PATH="user/";
	private interface Page{
		String LIST=PATH+"list";
		String EDIT=PATH+"edit";
	}
	//TODO 系统用户功能待进一步完善
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	SymemberService symemberService;
	
	
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
		Role userRole=this.symemberService.findByUser(user);
		map.put("userRole", userRole);
		map.put("enableds", EnabledEnum.values());
		List<Role> roles = this.roleService.findByRoleType(RoleType.system);
		map.put("roles", roles);
		return Page.EDIT;
	}
	
	private User createUser(){
		User user=new User();
		user.setPassword("0");
		user.setEnabled(EnabledEnum.y);
		return user;
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String save(@RequestParam("key")String userKey,@RequestParam("roleKey")String roleKey,HttpServletRequest request,RedirectAttributes redirectAttributes){		
		User user = this.userService.exist(userKey)?this.userService.find(userKey):createUser();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(user);
		binder.bind(request);
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
