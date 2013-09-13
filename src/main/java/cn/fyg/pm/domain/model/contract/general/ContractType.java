package cn.fyg.pm.domain.model.contract.general;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ContractType implements CommonEnum {
	construct("施工服务合同","E"),
	meter("材料采购合同","M"),
	design("设计施工合同","D");
	
	private final String name;
	
	private final String code;
	
	private ContractType(String name,String code){
		this.name=name;
		this.code=code;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public String getCode(){
		return this.code;
	}

}
