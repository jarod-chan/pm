package cn.fyg.pm.domain.model.nogenerator.look;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;



public interface LockService {
	
	/**
	 * 获得编号锁
	 * @param pattern
	 * @return
	 */
	Lock getLock(Pattern<? extends Object> pattern);
	

}
