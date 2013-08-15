package cn.fyg.pm.domain.shared;


public enum BusiCode implements CommonEnum {
	pm_contract("项目合同"),
	pm_contractmeter("采购合同"),
	
	pm_constructcont("施工联系"),
	pm_constructcert("施工签证"),
	
	pm_purchasereq("采购申请"),
	pm_purchasecert("价格确认"),
	
	pm_technoti("问题通知单"),
	pm_techcont("技术联系单");
	
	private final String name;
	
	private BusiCode(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
