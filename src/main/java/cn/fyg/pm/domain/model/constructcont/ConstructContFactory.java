package cn.fyg.pm.domain.model.constructcont;

import java.util.Date;
import java.util.UUID;

import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class ConstructContFactory {
	
	public static ConstructCont create(User creater,Project project, ConstructContState state){
		ConstructKey constructKey=new ConstructKey();
		constructKey.setProject(project);
		
		ConstructCont constructCont = new ConstructCont();
		constructCont.setNo(UUID.randomUUID().toString().toUpperCase().substring(0,4));
		constructCont.setState(state);
		constructCont.setCreater(creater);
		constructCont.setCreatedate(new Date());
		constructCont.setConstructKey(constructKey);
		constructCont.setLeader(project.getLeader());
		return constructCont;
	}

}
