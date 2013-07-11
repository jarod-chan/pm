package cn.fyg.pm.domain.model.contract;

import cn.fyg.pm.domain.shared.CommonEnum;

/**
 *专业分类
 */
public enum ContractSpec implements CommonEnum {
	
	municipal("市政工程"),
	landscape("景观绿化"),
	erection("安装工程"),
	decoration("装饰装修"), 
	civil("土建工程");
	
	
	private final String name;
	
	private ContractSpec(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}


}
