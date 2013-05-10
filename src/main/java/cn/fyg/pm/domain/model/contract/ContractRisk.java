package cn.fyg.pm.domain.model.contract;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ContractRisk implements CommonEnum {
	low("低"),
	middle("中"),
	high("高"),
	very_high("很高");
	
	private final String name;
	
	private ContractRisk(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
