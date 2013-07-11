package cn.fyg.pm.interfaces.web.module.project;

import cn.fyg.pm.domain.model.user.User;

public class PjmemberDto {
	
	private boolean checked;
	
	private User user;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
