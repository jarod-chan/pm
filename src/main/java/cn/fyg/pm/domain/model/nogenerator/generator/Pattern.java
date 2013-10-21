package cn.fyg.pm.domain.model.nogenerator.generator;

import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

public interface Pattern<T> {
	
	/**
	 * 初始化编码对象
	 * @param t
	 * @return
	 */
	Pattern<T> init(T t); 
	
	/**
	 * 是否空模式
	 * 空模式时，编号累加器不执行具体操作
	 * @return
	 */
	boolean isEmpty();
	
	/**
	 * 编码器执行的条件
	 */
	Pattern<T> setEmpty(boolean isEmpty);
	
	/**
	 * 返回编码模式
	 * @return
	 */
	NoKey getNoKey();

	/**
	 * 返回编码的最大显示
	 * @return
	 */
	Long getLimmit();
	
	/**
	 * 返回编码
	 * @return
	 */
	String getNo();
	
	/**
	 * 设置编码
	 */
	void setNo(String no);

}
