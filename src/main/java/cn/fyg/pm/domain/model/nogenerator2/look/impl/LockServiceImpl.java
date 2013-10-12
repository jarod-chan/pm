package cn.fyg.pm.domain.model.nogenerator2.look.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator2.NoPattern;
import cn.fyg.pm.domain.model.nogenerator2.look.Lock;
import cn.fyg.pm.domain.model.nogenerator2.look.LockService;

@Service("lockService")
public class LockServiceImpl implements LockService {
	
	private Map<NoKey,Lock> map=new HashMap<NoKey, Lock>();
	
	private Lock empty=new EmptyLock();


	@Override
	public synchronized Lock getLock(NoPattern pattern) {
		return getFromLockMap(pattern);
	}


	@Override
	public synchronized Lock getLock(boolean lockCondition, NoPattern pattern) {
		if(lockCondition){
			return getFromLockMap(pattern);
		}
		return this.empty;
	}

	private Lock getFromLockMap(NoPattern pattern) {
		NoKey noKey = pattern.getNoKey();
		Lock lock=map.get(noKey);
		if(lock==null){
			lock=new LockImp();
			map.put(noKey, lock);
		}
		return lock;
	}
}
