package cn.fyg.pm.domain.model.role;

import cn.fyg.pm.domain.shared.CommonEnum;

/**
 *角色类型
 *适用于项目角色分配，系统角色分配
 */
public enum RoleType implements CommonEnum {
	system("系统角色"),
	project("项目角色");
	
	private final String name;
	
	private RoleType(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
