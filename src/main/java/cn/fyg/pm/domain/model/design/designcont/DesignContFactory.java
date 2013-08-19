package cn.fyg.pm.domain.model.design.designcont;

import java.util.Date;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class DesignContFactory {

	public static DesignCont create(User creater, User projectLeader,Project project,DesignContState state) {
		
		DesignCont designCont = new DesignCont();
		designCont.setProject(project);
		designCont.setState(state);
		designCont.setCreater(creater);
		designCont.setCreatedate(new Date());
		designCont.setLeader(projectLeader);
		return designCont;
	}

}
