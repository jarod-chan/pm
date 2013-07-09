package cn.fyg.pm.domain.model.nogenerator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
import cn.fyg.pm.domain.model.nogenerator.NoRecord;
import cn.fyg.pm.domain.model.nogenerator.NoRecordRepository;


@Service("noGeneratorBusi")
public class NoGeneratorBusiImpl implements NoGeneratorBusi {
	
	@Autowired
	NoRecordRepository noRecordRepository;

	@Override
	public void generateNextNo(NoPatternUnit noPatternUnit){
		NoPattern noPattern = noPatternUnit.getNoPattern();
		if(!this.noRecordRepository.exists(noPattern.getNoKey())){
			NoRecord noRecord=new NoRecord(noPattern.getNoKey(),Long.valueOf(0),noPattern.getLimmit());
			this.noRecordRepository.save(noRecord);
		}
		NoRecord noRecord = this.noRecordRepository.findByNoKey(noPattern.getNoKey());
		String nextNo=noRecord.generateNextNo();
		noPatternUnit.setGenerateNo(nextNo);
	}
	
	@Override
	public void rollbackLastNo(NoPatternUnit noPatternUnit) throws NoNotLastException{
		NoPattern noPattern = noPatternUnit.getNoPattern();
		NoRecord noRecord = this.noRecordRepository.findByNoKey(noPattern.getNoKey());
		if(!thisIsLastNo(noRecord,noPatternUnit.getGenerateNo())){
			throw new NoNotLastException(String.format("对象编码[%s]不是最新数据，无法删除", noPatternUnit.getGenerateNo()));
		}
		noRecord.generatePrevNo();
	}

	private boolean thisIsLastNo(NoRecord noRecord,String unitNo) {
		if(noRecord==null){
			return false;
		}
		return noRecord.generateCurrNo().equals(unitNo);
	}

}
