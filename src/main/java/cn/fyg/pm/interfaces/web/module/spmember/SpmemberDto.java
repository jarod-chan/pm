package cn.fyg.pm.interfaces.web.module.spmember;

import cn.fyg.pm.domain.model.spmember.Spmember;
import cn.fyg.pm.domain.model.user.User;

public class SpmemberDto {
	
	private User user;
	 
	private Spmember spmember;


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Spmember getSpmember() {
		return spmember;
	}

	public void setSpmember(Spmember spmember) {
		this.spmember = spmember;
	}
	 
	 

}
