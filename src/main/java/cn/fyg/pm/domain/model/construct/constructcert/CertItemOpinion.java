package cn.fyg.pm.domain.model.construct.constructcert;

import cn.fyg.pm.domain.shared.CommonEnum;

public enum CertItemOpinion implements CommonEnum {
	agree("同意"),
	disagree("不同意");

	private final String name;
	
	private CertItemOpinion(String name){
		this.name=name;
	}

	@Override
	public String getName() {
		return this.name;
	}
}
