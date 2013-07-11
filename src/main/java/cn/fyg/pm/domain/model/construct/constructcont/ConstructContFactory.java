package cn.fyg.pm.domain.model.construct.constructcont;

import java.math.BigDecimal;
import java.util.Date;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class ConstructContFactory {
	
	public static ConstructCont create(User creater,Project project, ConstructContState state){
		ConstructKey constructKey=new ConstructKey();
		constructKey.setProject(project);
		
		ConstructCont constructCont = new ConstructCont();
		constructCont.setState(state);
		constructCont.setCreater(creater);
		constructCont.setCreatedate(new Date());
		constructCont.setConstructKey(constructKey);
		constructCont.setLeader(project.getLeader());
		constructCont.setTolsum(new BigDecimal("0.00"));
		return constructCont;
	}

}
