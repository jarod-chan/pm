package cn.fyg.pm.interfaces.web.module.spmember;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.SpmemberService;

@Component
public class SpmemberFacade {
	
	@Autowired 
	SpmemberService spmemberServiceRefactor;

	@Transactional
	public void saveSpmembers(List<SpmemberDto> spmembers) {
		for (SpmemberDto spmemberDto : spmembers) {
			if(spmemberDto.getSupplier().getId()!=null){
				spmemberServiceRefactor.assignUserSupplier(spmemberDto.getUser(), spmemberDto.getSupplier());
			}else{
				spmemberServiceRefactor.clearUserSupplier(spmemberDto.getUser());
			}
		}
	}
	
	

}
