package cn.fyg.pm.interfaces.web.module.spmember;

import java.util.List;

import cn.fyg.pm.domain.model.spmember.Spmember;

public class SpmemberPage {
	
	private List<Spmember> spmembers;

	public List<Spmember> getSpmembers() {
		return spmembers;
	}

	public void setSpmembers(List<Spmember> spmembers) {
		this.spmembers = spmembers;
	}

	
}
