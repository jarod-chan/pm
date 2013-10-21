package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertRepository;
import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.service.GeneService;

@Component("constructCertServiceExd")
public class ConstructCertServiceExd {

	@Autowired
	ConstructCertRepository constructCertRepository;
	@Autowired
	GeneService geneService;

	@Transactional
	public ConstructCert save(ConstructCert constructCert,Pattern<ConstructCert> pattern) {
		this.geneService.generateNextNo(pattern);
		return this.constructCertRepository.save(constructCert);
	}

	@Transactional
	public void finish(ConstructCert constructCert,
			Pattern<ConstructCert> pattern) {
		this.geneService.generateNextNo(pattern);
		this.constructCertRepository.save(constructCert);
		
	}

}
