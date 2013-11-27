package cn.fyg.pm.application;

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
	
	void delete(Long designContId);
	
	void sendLog(Long designContId,String receiver,Long sendnumb);
	
}
