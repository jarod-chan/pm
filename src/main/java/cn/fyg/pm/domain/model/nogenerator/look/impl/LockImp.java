package cn.fyg.pm.domain.model.nogenerator.look.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.fyg.pm.domain.model.nogenerator.look.Lock;

/**
 *锁对象
 */
public class LockImp implements Lock {
	
	private static final Logger logger=LoggerFactory.getLogger(LockImp.class);
	
	private boolean isLock=false;
	
	@Override
	public synchronized void lock(){
		while(isLock){
			try {
				wait();
			} catch (InterruptedException e) {
				logger.error("can't wait this thread",e);
			}
		}
		this.isLock=true;
	}
	
	@Override
	public synchronized void unlock(){
		this.isLock=false;
		this.notifyAll();
	}

}
