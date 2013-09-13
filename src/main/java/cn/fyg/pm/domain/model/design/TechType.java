package cn.fyg.pm.domain.model.design;

import cn.fyg.pm.domain.shared.CommonEnum;

/**
 *技术分类
 */
public enum TechType implements CommonEnum {
	type1("T1","类别1"),
	type2("T2","类别2");
	
	private final String code;
	private final String name;
	
	private TechType(String code,String name) {
		this.code=code;
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public String getCode() {
		return code;
	}
	
	
}
