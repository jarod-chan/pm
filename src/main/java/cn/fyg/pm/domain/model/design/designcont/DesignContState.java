package cn.fyg.pm.domain.model.design.designcont;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum DesignContState implements CommonEnum {
	
	new_("新建"),
	saved("已保存"),
	commit("已提交"),
	finish("已完成"),
	invalid("已作废");
	
	private final String name;
	
	private DesignContState(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}


}
