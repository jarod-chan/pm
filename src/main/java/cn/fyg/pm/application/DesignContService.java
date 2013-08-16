package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.CommitValidator;
import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface DesignContService extends ServiceQuery<DesignCont>,CommitValidator<DesignCont> {

	DesignCont find(Long designContId);

	DesignCont create(User creater,Project project,DesignContState state);

	DesignCont save(DesignCont designCont);
	
	DesignCont finish(Long designContId,String userKey);
	
	List<DesignCont> findByProject(Project project,DesignContState state);
	
//	DesignCont findByDesignContKey(DesignKey designKey);

	void delete(Long designContId);
	
}
