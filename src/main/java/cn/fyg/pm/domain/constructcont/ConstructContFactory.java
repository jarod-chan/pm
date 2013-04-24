package cn.fyg.pm.domain.constructcont;

import java.util.Date;
import java.util.UUID;

import cn.fyg.pm.domain.user.User;

public class ConstructContFactory {
	
	public static ConstructCont create(User creater){
		ConstructCont constructCont = new ConstructCont();
		constructCont.setNo(UUID.randomUUID().toString().toUpperCase().substring(0,4));
		constructCont.setState(ConstructContState.new_);
		constructCont.setCreater(creater);
		constructCont.setCreatedate(new Date());
		return constructCont;
	}

}
