package cn.fyg.pm.interfaces.web.module.project.pjmember;

import java.util.ArrayList;
import java.util.List;

import cn.fyg.pm.domain.model.pjmember.Pjmember;

public class PjmemberPage {
	
	private List<Pjmember> pjmembers=new ArrayList<Pjmember>();

	public List<Pjmember> getPjmembers() {
		return pjmembers;
	}

	public void setPjmembers(List<Pjmember> pjmembers) {
		this.pjmembers = pjmembers;
	}

	
}
