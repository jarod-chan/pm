package cn.fyg.pm.domain.model.design.designnoti;

import java.util.Date;

import cn.fyg.pm.domain.model.design.designkey.DesignKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class DesignNotiFactory {
	
	public static DesignNoti create(User creater,User projectLeader, Project project,
			DesignNotiState state) {
		DesignKey designKey=new DesignKey();
		designKey.setProject(project);
		
		DesignNoti designNoti = new DesignNoti();
		designNoti.setDesignKey(designKey);
		designNoti.setState(state);
		designNoti.setCreater(creater);
		designNoti.setCreatedate(new Date());
		designNoti.setLeader(projectLeader);
		return designNoti;
	}

}
