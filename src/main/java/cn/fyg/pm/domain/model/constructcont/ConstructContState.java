package cn.fyg.pm.domain.model.constructcont;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ConstructContState implements CommonEnum {
	new_("新建"),
	saved("已保存"),
	commit("已提交"),
	finish("已完成");
	
	private final String name;
	
	private ConstructContState(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
