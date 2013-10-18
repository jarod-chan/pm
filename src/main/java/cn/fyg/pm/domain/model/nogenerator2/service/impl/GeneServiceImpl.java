package cn.fyg.pm.domain.model.nogenerator2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecord2;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecord2Repository;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecordService;
import cn.fyg.pm.domain.model.nogenerator2.generator3.Pattern;
import cn.fyg.pm.domain.model.nogenerator2.service.GeneService;

@Component("geneService")
public class GeneServiceImpl implements GeneService {
	
	@Autowired
	NoRecordService noRecordService;
	@Autowired
	NoRecord2Repository noRecordRepository;

	@Override
	public void generateNextNo(Pattern<? extends Object> pattern) {
		if(pattern.isEmpty()) return;
		NoKey noKey = pattern.getNoKey();
		if(!this.noRecordRepository.exists(noKey)){
			NoRecord2 noRecord=new NoRecord2(noKey,0L,pattern.getLimmit());
			this.noRecordRepository.save(noRecord);
		}
		NoRecord2 noRecord=this.noRecordRepository.findOne(noKey);
		pattern.setNo(noRecord.nextNo());
		
	}

	@Override
	public void rollbackLastNo(Pattern<? extends Object> pattern)
			throws NoNotLastException {
		if(pattern.isEmpty()) return;
		NoKey noKey = pattern.getNoKey();
		NoRecord2 noRecord=this.noRecordRepository.findOne(noKey);
		noRecord.prevNo(pattern.getNo());
	}


}
