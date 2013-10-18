package cn.fyg.pm.domain.model.nogenerator2.look.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator2.generator3.Pattern;
import cn.fyg.pm.domain.model.nogenerator2.look.Lock;
import cn.fyg.pm.domain.model.nogenerator2.look.LockService;

@Service("lockService")
public class LockServiceImpl implements LockService {
	
	private Map<NoKey,Lock> map=new HashMap<NoKey, Lock>();

	@Override
	public synchronized Lock getLock(Pattern<? extends Object> pattern) {
		return getFromLockMap(pattern);
	}



	private Lock getFromLockMap(Pattern<? extends Object> pattern) {
		NoKey noKey = pattern.getNoKey();
		Lock lock=map.get(noKey);
		if(lock==null){
			lock=new LockImp();
			map.put(noKey, lock);
		}
		return lock;
	}
}
