package cn.fyg.pm.interfaces.web.shiro;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.oval.internal.util.ArrayUtils;

public enum Module {
	system("system:menu",new String[]{
			"project:menu",
			"spmember:menu",
			"user:menu",
			"user:info"
	}),
	supplier("supplier:menu",new String[]{
			"supplier-contra:menu",
			"supplier-construct:menu",
			"supplier-meter:menu",
			"supplier-design:menu"
	}),
	contract("contract:menu",new String[]{
			"contract-construct:menu",
			"contract-meter:menu",
			"contract-design:menu"
	}),
	trace("trace:menu",new String[]{
			"constructcont:menu",
			"constructcert:menu",
			"purchasereq:menu",
			"purchasecert:menu",
			"designnoti:menu",
			"designcont:menu",
			"spconstructcont:menu",
			"spconstructcert:menu"
	}),
	;
	
	private static final Map<String,String> map=new HashMap<String,String>();
	
	static{
		Module[] modules = values();
		for (Module module : modules) {
			ModuleMenu moduleMenu = module.moduleMenu();
			for(String accessPermissionKey:moduleMenu.getAccessPermissionKey()){
				map.put(accessPermissionKey, moduleMenu.getKey());
			}
		}
	}
	
	/**
	 * 返回菜单权限
	 * @param permissions
	 * @return
	 */
	public static Set<String>  getModuleMenuPermission(Collection<String> permissions){
		Set<String> set=new HashSet<String>();
		for(String permission:permissions){
			String menuPermission=map.get(permission);
			if(menuPermission!=null){
				set.add(menuPermission);
			}
		}
		return set;
	}
	
	private ModuleMenu moduleMenu;

	private Module(String permissionKey,String[] accessPermissionKey) {
		this.moduleMenu = new ModuleMenu().setKey(permissionKey).setAccessPermissionKey(ArrayUtils.asList(accessPermissionKey));
	}
	
	private ModuleMenu moduleMenu(){
		return this.moduleMenu;
	}
	
	

}
