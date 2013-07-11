package cn.fyg.pm.domain.model.construct.constructcert;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ConstructCertState implements CommonEnum {
	new_("新建"),
	saved("已保存"),
	commit("已提交"),
	finish("已完成");

	private final String name;
	
	private ConstructCertState(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
