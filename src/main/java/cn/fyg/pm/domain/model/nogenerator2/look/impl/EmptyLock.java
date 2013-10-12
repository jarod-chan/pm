package cn.fyg.pm.domain.model.nogenerator2.look.impl;

import cn.fyg.pm.domain.model.nogenerator2.look.Lock;


/**
 *空锁对象
 */
public class EmptyLock implements Lock {

	@Override
	public void lock() {
	}

	@Override
	public void unlock() {
		
	}
	

}
