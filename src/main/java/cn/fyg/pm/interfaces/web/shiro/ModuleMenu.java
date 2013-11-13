package cn.fyg.pm.interfaces.web.shiro;

import java.util.List;

/**
 *页面模块菜单权限
 *用于页面的菜单模块设计
 */
public class ModuleMenu {
	
	/**
	 * 权限名
	 */
	private String key;
	
	private List<String> accessPermissionKey;

	public String getKey() {
		return key;
	}

	public ModuleMenu setKey(String key) {
		this.key = key;
		return this;
	}

	public List<String> getAccessPermissionKey() {
		return accessPermissionKey;
	}

	public ModuleMenu setAccessPermissionKey(List<String> accessPermissionKey) {
		this.accessPermissionKey = accessPermissionKey;
		return this;
	} 

}
