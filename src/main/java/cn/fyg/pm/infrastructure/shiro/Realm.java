package cn.fyg.pm.infrastructure.shiro;

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

/**
 * shiro 用户校验认证
 * 认证，授权用户代码
 * 在spring-shiro.xml 配置文件中指定
 */
public class Realm extends AuthorizingRealm{

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		if (principals == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }
		
        String username=(String) principals.fromRealm(getName()).iterator().next();  
        if(username!=null&&username.equals("user")){  
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();  
            //登录的用户有多少个角色  
            info.addRole("admin");
            info.addRole("testRole");
            //权限
            info.addStringPermission("user:create");
            info.addStringPermission("user:edit");
            info.addStringPermission("user:menu");
            return info;  
        }  
        return null;  

	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		 UsernamePasswordToken upt = (UsernamePasswordToken) token;  
	     if(upt.getUsername().equals("user")){
	    	  return new SimpleAuthenticationInfo(upt.getUsername(), "123", getName());  
	     }
	     return null;  
	}





	
}
