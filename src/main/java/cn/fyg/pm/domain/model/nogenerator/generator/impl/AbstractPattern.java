package cn.fyg.pm.domain.model.nogenerator.generator.impl;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

public abstract class AbstractPattern<T> implements Pattern<T> {
	
	private NoKey noKey;
	
	private Long Limmit;
	
	private boolean isEmpty;
	
	public T t;

//	public AbstractPattern(){
//		noKey=new NoKey();
//		initNoKey(noKey,this.t);
//		this.Limmit=initLimmit();
//	}
	
	@Override
	public Pattern<T> init(T t) {
		this.t=t;
		this.noKey=new NoKey();
		initNoKey(noKey,this.t);
		this.Limmit=initLimmit();
		return this;
	}
	
	@Override
	public boolean isEmpty() {
		return this.isEmpty;
	}
	
	@Override
	public Pattern<T> setEmpty(boolean isEmpty){
		this.isEmpty=isEmpty;
		return this;
	}

	@Override
	public NoKey getNoKey() {
		return this.noKey;
	}

	@Override
	public Long getLimmit() {
		return this.Limmit;
	}
	
	public abstract Long initLimmit();
	
	public abstract void initNoKey(NoKey noKey,T t);

}
