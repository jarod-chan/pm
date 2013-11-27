package cn.fyg.pm.application;

import cn.fyg.pm.application.common.CommitValidator;
import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface DesignNotiService extends ServiceQuery<DesignNoti>,
		CommitValidator<DesignNoti> {

	DesignNoti find(Long designNotiId);

	DesignNoti create(User creater, Project project, DesignNotiState state);

	DesignNoti save(DesignNoti designNoti);

	void delete(Long designNotiId);

	void finish(Long designNotiId, String userKey);

}
