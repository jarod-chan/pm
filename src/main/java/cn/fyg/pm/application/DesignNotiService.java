package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.CommitValidator;
import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface DesignNotiService extends ServiceQuery<DesignNoti>,CommitValidator<DesignNoti> {

	DesignNoti find(Long designNotiId);

	DesignNoti create(User creater, Project project, DesignNotiState state);

	DesignNoti save(DesignNoti designNoti);
	
	DesignNoti finish(Long designNotiId,String userKey);
	
	List<DesignNoti> findByProject(Project project,DesignNotiState state);
	
//	DesignNoti findByDesignKey(DesignKey designKey);

	void delete(Long designNotiId);
	
}
