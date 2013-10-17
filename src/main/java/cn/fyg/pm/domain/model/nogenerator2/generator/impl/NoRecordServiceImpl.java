package cn.fyg.pm.domain.model.nogenerator2.generator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecord2;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecord2Repository;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecordService;

@Service("noRecordService")
public class NoRecordServiceImpl implements NoRecordService {
	
	@Autowired
	NoRecord2Repository noRecordRepository;
	

	@Override
	public String generateNextNo(NoPattern pattern) {
		NoKey noKey = pattern.getNoKey();
		if(!this.noRecordRepository.exists(noKey)){
			NoRecord2 noRecord=new NoRecord2(noKey,0L,pattern.getLimmit());
			this.noRecordRepository.save(noRecord);
		}
		NoRecord2 noRecord=this.noRecordRepository.findOne(noKey);
		return noRecord.nextNo();
	}

	@Override
	public void rollbackLastNo(NoPattern pattern,String lastNo) throws NoNotLastException {
		NoKey noKey = pattern.getNoKey();
		NoRecord2 noRecord=this.noRecordRepository.findOne(noKey);
		noRecord.prevNo(lastNo);
	}

}
