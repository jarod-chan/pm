package cn.fyg.pm.domain.model.nogenerator2.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator2.NoPattern;
import cn.fyg.pm.domain.model.nogenerator2.NoRecord2;
import cn.fyg.pm.domain.model.nogenerator2.NoRecordRepository;
import cn.fyg.pm.domain.model.nogenerator2.NoRecordService;

@Service("noRecordService")
public class NoRecordServiceImpl implements NoRecordService {
	
	@Autowired
	NoRecordRepository noRecordRepository;
	
	private Map<NoKey,NoRecord2> map=new HashMap<NoKey,NoRecord2>();

	@Override
	public synchronized NoRecord2 getNoRecord(NoPattern pattern) {
		NoKey noKey = pattern.getNoKey();
		NoRecord2 noRecord = this.map.get(noKey);
		if(noRecord==null){
			if(this.noRecordRepository.exists(noKey)){
				noRecord=this.noRecordRepository.findOne(noKey);
			}else{
				noRecord=new NoRecord2(noKey,0L,pattern.getLimmit());
				noRecord=this.noRecordRepository.save(noRecord);
				this.map.put(noKey, noRecord);
			}
		}
		return noRecord;
	}

	@Override
	public NoRecord2 save(NoRecord2 noRecord) {
		return this.noRecordRepository.save(noRecord);
	}

}
