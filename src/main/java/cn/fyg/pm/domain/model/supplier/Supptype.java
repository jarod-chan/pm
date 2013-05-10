package cn.fyg.pm.domain.model.supplier;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum Supptype implements CommonEnum {
	
	contra("内部承包人"),
	construct("工程服务商"),
	meter("材料供应商"),
	design("设计服务商");

	private final String name;
	
	Supptype(String name){
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
