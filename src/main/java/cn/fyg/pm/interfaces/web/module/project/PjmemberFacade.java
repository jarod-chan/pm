package cn.fyg.pm.interfaces.web.module.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.PjmemberService;
import cn.fyg.pm.domain.model.project.Project;

@Component
public class PjmemberFacade {
	
	@Autowired
	PjmemberService pjmemberService;
	
	@Transactional
	public void savePjmember(Project project,List<PjmemberDto> pjmemberDtos){
		for (PjmemberDto pjmemberDto : pjmemberDtos) {
			if(pjmemberDto.isChecked()){
				pjmemberService.appendPrjectUser(project, pjmemberDto.getUser());
			}else{
				pjmemberService.removeProjectUser(project, pjmemberDto.getUser());
			}
		}
	}

}
