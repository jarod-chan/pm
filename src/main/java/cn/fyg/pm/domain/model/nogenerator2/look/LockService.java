package cn.fyg.pm.domain.model.nogenerator2.look;

import cn.fyg.pm.domain.model.nogenerator2.generator.NoPattern;


public interface LockService {
	
	/**
	 * 获得编号锁
	 * @param pattern
	 * @return
	 */
	Lock getLock(NoPattern pattern);
	
	/**
	 * 在条件为true时，才返回编号锁，否则返回空锁
	 * 解决对象为空不加锁的判断问题
	 * @param lockCondition
	 * @param pattern
	 * @return
	 */
	Lock getLock(boolean lockCondition,NoPattern pattern);

}
