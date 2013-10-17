package cn.fyg.pm.domain.model.nogenerator2.generator;

import cn.fyg.pm.domain.model.nogenerator2.look.Lock;


public interface PatternGene<T> {
	
	/**
	 * 返回锁
	 * @return
	 */
	Lock getLock();
	
	/**
	 * 锁条件,为真时返回锁，可以执行生成编码的操作
	 * @return
	 */
	boolean lockCondition();
	
	/**
	 * 初始化关联对象，当对象编号生成跟对象具体属性存在关系时
	 * 可以获得对象属性
	 * @param object
	 */
	void init(T object);
	
	/**
	 * 返回某类业务数据的编码格式
	 * @return
	 */
	NoPattern getNoPattern();
	
	
	/**
	 * 设置编号
	 * @param generateNo
	 */
	void setNo(String no);
	
	/**
	 * 返回编号
	 * @return 
	 */
	String getNo();
	

}
