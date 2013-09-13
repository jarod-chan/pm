package cn.fyg.pm.domain.model.design.designcont.reason;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum Type implements CommonEnum {
	
	design("1","设计类"),
	construct("2","现场施工类"),
	sale("3","营销类"),
	other("4","其它");
	
	private final String en;//显示序号
	private final String name;//显示名称
	
	private Type(String en,String name) {
		this.en=en;
		this.name=name;
	}
	
	public String getEn() {
		return en;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
