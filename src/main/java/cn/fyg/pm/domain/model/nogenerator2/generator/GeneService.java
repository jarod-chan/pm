package cn.fyg.pm.domain.model.nogenerator2.generator;

import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator2.look.Lock;

public interface GeneService<T> {
	
	
	/**
	 * 生成下一个编号
	 * @param noPatternUnit
	 */
	public void generateNextNo(PatternGene<T> patternGene,T object);
	
	/**
	 * 回滚最后一个编号，如果不是最后一个编号，则抛出异常
	 * @param noPatternUnit
	 * @throws NoNotLastException
	 */
	public void rollbackLastNo(PatternGene<T> patternGene,T object) throws NoNotLastException;

}
