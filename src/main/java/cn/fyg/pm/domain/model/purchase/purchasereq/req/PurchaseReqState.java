package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum PurchaseReqState implements CommonEnum {
	
	new_("新建"),
	saved("已保存"),
	commit("已提交"),
	finish("已完成"),
	invalid("已作废");
	
	private final String name;
	
	private PurchaseReqState(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
