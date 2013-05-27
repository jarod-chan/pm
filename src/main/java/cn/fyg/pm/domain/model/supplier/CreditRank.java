package cn.fyg.pm.domain.model.supplier;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum CreditRank implements CommonEnum {
	
	one("一级"),
	two("二级"),
	there("三级"),
	four("四级"),
	five("五级");

	private final String name;
	
	private CreditRank(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
