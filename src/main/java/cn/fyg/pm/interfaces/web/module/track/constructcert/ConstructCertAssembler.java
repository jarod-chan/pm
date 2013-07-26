package cn.fyg.pm.interfaces.web.module.track.constructcert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;

@Component
public class ConstructCertAssembler {
	
	@Autowired
	ConstructContService constructContService;
	
	public  List<ConstructCertDto> create(List<ConstructCert> constructCertList){
		List<ConstructCertDto> constructCertDtoList = new ArrayList<ConstructCertDto>();
		for (ConstructCert constructCert : constructCertList) {
			ConstructCont constructCont = constructContService.findByConstructKey(constructCert.getConstructKey());
			ConstructCertDto constructCertDto = new ConstructCertDto();
			constructCertDto.setConstructCont(constructCont);
			constructCertDto.setConstructCert(constructCert);
			constructCertDtoList.add(constructCertDto);
		}
		return constructCertDtoList;
	}

}
