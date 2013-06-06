package cn.fyg.pm.domain.model.purchase.purchasereq;

import cn.fyg.pm.domain.shared.CommonEnum;


public enum UptypeEnum implements CommonEnum { 
	
	pm_purchasecert("价格确认单"),
	pm_contractmeter("采购合同");
	
	private final String name;
	
	private UptypeEnum(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
