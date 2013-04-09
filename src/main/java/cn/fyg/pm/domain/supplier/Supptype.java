package cn.fyg.pm.domain.supplier;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum Supptype implements CommonEnum {
	
	meter("材料供应商"),
	design("设计院"),
	supvr("监理公司"),
	contra("承包商");

	private String name;
	
	Supptype(String name){
		this.name=name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
