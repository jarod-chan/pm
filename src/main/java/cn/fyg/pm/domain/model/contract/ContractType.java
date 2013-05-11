package cn.fyg.pm.domain.model.contract;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ContractType implements CommonEnum {
	construct("施工服务合同"),
	meter("材料采购合同"),
	design("设计施工合同");
	
	private final String name;
	
	private ContractType(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
