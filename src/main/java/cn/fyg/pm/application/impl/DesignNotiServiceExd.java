package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiItem;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;

@Component("designNotiServiceExd")
public class DesignNotiServiceExd {
	
	@Autowired
	DesignNotiRepository designNotiRepository;
	@Autowired
	GeneService geneService;

	@Transactional
	public DesignNoti save(DesignNoti designNoti, Pattern<DesignNoti> pattern) {
		this.geneService.generateNextNo(pattern);
		for(DesignNotiItem designNotiItem:designNoti.getDesignNotiItems()){
			designNotiItem.setDesignNoti(designNoti);
		}
		return this.designNotiRepository.save(designNoti);
	}

	@Transactional
	public void finish(DesignNoti designNoti, Pattern<DesignNoti> pattern) {
		this.geneService.generateNextNo(pattern);
		this.designNotiRepository.save(designNoti);
	}

}
