package cn.fyg.pm.interfaces.web.shiro;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.fyg.pm.application.IdentifyService;
import cn.fyg.pm.application.UserService;
import cn.fyg.pm.domain.model.user.User;

/**
 * shiro 用户校验认证
 * 认证，授权用户代码
 * 在spring-shiro.xml 配置文件中指定
 */
public class Realm extends AuthorizingRealm{
	
	IdentifyService identifyService;

	UserService userService;	

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setIdentifyService(IdentifyService identifyService) {
		this.identifyService = identifyService;
	}


	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("Principal对象不能为空");
		}

		String username = (String) principals.fromRealm(getName()).iterator().next();

		if (StringUtils.isNotBlank(username)) {
			User user = new User();
			user.setKey(username);
			List<String> permissions = this.identifyService.findUserPermission(user);
			
			//一般权限
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermissions(permissions);
			
			//菜单权限
			Set<String> moduleMenuPermission = Module.getModuleMenuPermission(permissions);
			info.addStringPermissions(moduleMenuPermission);
			return info;
		}
		return null;
	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		 UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;  
		 String username = usernamePasswordToken.getUsername();
		 User user=this.userService.find(username);
		 if(user!=null){
			 return new SimpleAuthenticationInfo(user.getKey(), user.getPassword(), getName());  
		 }
	     return null;  
	}





	
}
