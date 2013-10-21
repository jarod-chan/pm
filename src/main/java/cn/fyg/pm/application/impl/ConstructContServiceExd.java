package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;

@Component("constructContServiceExd")
public class ConstructContServiceExd {
	
	@Autowired
	ConstructContRepository constructContRepository;
	@Autowired
	GeneService geneService;

	@Transactional
	public ConstructCont save(ConstructCont constructCont,Pattern<ConstructCont> pattern) {
		this.geneService.generateNextNo(pattern);
		for(ConstructContItem constructContItem:constructCont.getConstructContItems()){
			constructContItem.setConstructCont(constructCont);
		}
		return this.constructContRepository.save(constructCont);
	}

	@Transactional
	public void finish(ConstructCont constructCont,Pattern<ConstructCont> pattern) {
		this.geneService.generateNextNo(pattern);
		this.constructContRepository.save(constructCont);
	}

}
