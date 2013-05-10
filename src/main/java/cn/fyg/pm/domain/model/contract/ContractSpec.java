package cn.fyg.pm.domain.model.contract;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ContractSpec implements CommonEnum {
	
	concrete("混凝土"),
	foundation("地基基础"),
	major_structure("主体结构"), 
	decoration("建筑装饰装修"), 
	building_roof("建筑屋面"),
	water_warm("给、排水及采暖"),
	architecture_electric("建筑电气及智能化"),
	ventilation("通风与空调"),
	elevator("电梯"), 
	energy_saving("建筑节能"),
	landscape_greening("景观绿化"),
	facilities("市政配套"), 
	other("其他");
	
	

	private final String name;
	
	private ContractSpec(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}


}
