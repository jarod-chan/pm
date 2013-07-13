package cn.fyg.pm.domain.model.user;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum EnabledEnum implements CommonEnum {
	y("启用"),
	n("停用");
	
	private final String name;
	
	private EnabledEnum(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
