package cn.fyg.pm.domain.model.supplier;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum Supptype implements CommonEnum {
	
	contra("内部承包人","I"),
	construct("工程服务商","E"),
	meter("材料供应商","M"),
	design("设计服务商","D");

	private final String name;
	private final String code;
	
	Supptype(String name,String code){
		this.name=name;
		this.code=code;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public String getCode(){
		return this.code;
	}

}
