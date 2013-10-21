package cn.fyg.pm.domain.model.nogenerator.service;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoNotLastException;

public interface GeneService {
	
	
	/**
	 * 生成下一个编号
	 * @param noPatternUnit
	 */
	public void generateNextNo(Pattern<? extends Object> pattern);
	
	/**
	 * 回滚最后一个编号，如果不是最后一个编号，则抛出异常
	 * @param noPatternUnit
	 * @throws NoNotLastException
	 */
	public void rollbackLastNo(Pattern<? extends Object> pattern) throws NoNotLastException;

}
