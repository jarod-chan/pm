package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContItem;
import cn.fyg.pm.domain.model.design.designcont.DesignContRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;

@Component("designContServiceExd")
public class DesignContServiceExd {
	
	@Autowired
	DesignContRepository designContRepository;
	@Autowired
	GeneService geneService;

	@Transactional
	public DesignCont save(DesignCont designCont, Pattern<DesignCont> pattern) {
		this.geneService.generateNextNo(pattern);
		for(DesignContItem designContItem:designCont.getDesignContItems()){
			designContItem.setDesignCont(designCont);
		}
		return this.designContRepository.save(designCont);
	}

	@Transactional
	public DesignCont finish(DesignCont designCont, Pattern<DesignCont> pattern) {
		this.geneService.generateNextNo(pattern);
		return this.designContRepository.save(designCont);
	}

}
