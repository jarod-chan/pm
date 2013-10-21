package cn.fyg.pm.domain.model.nogenerator.generator.impl;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.PatternFactory;

public abstract class AbstractPatternFactory<T> implements PatternFactory<T> {

	
	@Override
	public Pattern<T> create(T t) {
		return doCreate(t);
	}


	public  abstract Pattern<T> doCreate(T t);

}
