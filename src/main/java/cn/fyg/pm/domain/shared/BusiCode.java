package cn.fyg.pm.domain.shared;


public enum BusiCode implements CommonEnum {
	HT("合同"),
	SGLX("施工联系"),
	SGQZ("施工签证");
	
	private final String name;
	
	private BusiCode(String name) {
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
