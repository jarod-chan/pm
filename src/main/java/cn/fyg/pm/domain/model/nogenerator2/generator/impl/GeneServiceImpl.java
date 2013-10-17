package cn.fyg.pm.domain.model.nogenerator2.generator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.nogenerator2.generator.GeneService;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoRecordService;
import cn.fyg.pm.domain.model.nogenerator2.generator.PatternGene;

@Component("geneService")
public class GeneServiceImpl implements GeneService<Object> {
	
	@Autowired
	NoRecordService noRecordService;

	@Override
	public void generateNextNo(PatternGene<Object> patternGene, Object object) {
		patternGene.init(object);
		String no=noRecordService.generateNextNo(patternGene.getNoPattern());
		patternGene.setNo(no);
	}

	@Override
	public void rollbackLastNo(PatternGene<Object> patternGene, Object object)
			throws NoNotLastException {
		patternGene.init(object);
		noRecordService.rollbackLastNo(patternGene.getNoPattern(), patternGene.getNo());
	}


}
