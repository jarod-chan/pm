package cn.fyg.pm.domain.model.purchase.purchasecert;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum PurchaseCertState implements CommonEnum {
	
	new_("新建"),
	saved("已保存"),
	commit("已提交"),
	finish("已完成"),
	invalid("已作废");
	
	private final String name;
	
	private PurchaseCertState(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}