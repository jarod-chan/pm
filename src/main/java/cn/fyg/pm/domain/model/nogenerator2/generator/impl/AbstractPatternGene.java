package cn.fyg.pm.domain.model.nogenerator2.generator.impl;



import cn.fyg.pm.domain.model.nogenerator2.generator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator2.generator.PatternGene;
import cn.fyg.pm.domain.model.nogenerator2.look.Lock;
import cn.fyg.pm.domain.model.nogenerator2.look.LockService;

public abstract class AbstractPatternGene<T> implements PatternGene<T> {
	
	public T object;
	private NoPattern noPattern;
	public LockService lockService;
	public Lock lock;
	
	
	@Override
	public void init(T object) {
		this.object=object;
		this.noPattern=initNoPatternWithObject(this.object);
		this.lock=this.lockService.getLock(this.lockCondition(),this.noPattern);
	}
	
	@Override
	public NoPattern getNoPattern() {
		return this.noPattern;
	}
	
	@Override
	public Lock getLock() {
		return this.lock;
	}

	public abstract NoPattern initNoPatternWithObject(T object);
	
	public abstract void setLockService(LockService lockService);

}
