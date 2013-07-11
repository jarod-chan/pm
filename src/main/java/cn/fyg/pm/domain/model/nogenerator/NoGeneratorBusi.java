package cn.fyg.pm.domain.model.nogenerator;



public interface NoGeneratorBusi {
	
	/**
	 * 生成下一个编号
	 * @param noPatternUnit
	 */
	public void generateNextNo(NoPatternUnit noPatternUnit);
	
	/**
	 * 回滚最后一个编号，如果不是最后一个编号，则抛出异常
	 * @param noPatternUnit
	 * @throws NoNotLastException
	 */
	public void rollbackLastNo(NoPatternUnit noPatternUnit) throws NoNotLastException;

}
