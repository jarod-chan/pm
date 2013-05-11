package cn.fyg.pm.domain.shared;


public enum BusiCode implements CommonEnum {
	pm_contract("项目合同"),
	pm_constructcont("施工联系"),
	pm_constructcert("施工签证");
	
	private final String name;
	
	private BusiCode(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
