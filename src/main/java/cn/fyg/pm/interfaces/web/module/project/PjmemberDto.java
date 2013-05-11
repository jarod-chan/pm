package cn.fyg.pm.interfaces.web.module.project;

import cn.fyg.pm.domain.model.pjmember.Pjmember;

public class PjmemberDto {
	
	private boolean checked;
	
	private Pjmember pjmember;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Pjmember getPjmember() {
		return pjmember;
	}

	public void setPjmember(Pjmember pjmember) {
		this.pjmember = pjmember;
	}
	
	

}
