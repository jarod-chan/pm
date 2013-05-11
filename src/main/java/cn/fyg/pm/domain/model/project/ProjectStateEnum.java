package cn.fyg.pm.domain.model.project;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ProjectStateEnum implements CommonEnum {
	enabled("启用"),
	closed("关闭");

	private final String name;
	
	private ProjectStateEnum(String name){
		this.name=name;
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}

}
