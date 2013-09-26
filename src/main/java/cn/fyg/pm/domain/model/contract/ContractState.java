package cn.fyg.pm.domain.model.contract;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum ContractState  implements CommonEnum {
	new_("新建"),
	checking("审批中"),
	agree("同意签发"),
	finish("履约完成"),
	signed("双方签订"),
	closed("关闭");
	
	private final String name;
	
	private ContractState(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}


}
