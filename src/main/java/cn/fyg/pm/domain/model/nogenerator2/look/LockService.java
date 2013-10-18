package cn.fyg.pm.domain.model.nogenerator2.look;

import cn.fyg.pm.domain.model.nogenerator2.generator3.Pattern;



public interface LockService {
	
	/**
	 * 获得编号锁
	 * @param pattern
	 * @return
	 */
	Lock getLock(Pattern<? extends Object> pattern);
	

}
