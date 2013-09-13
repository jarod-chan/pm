package cn.fyg.pm.domain.model.design.designcont.reason;

import java.util.ArrayList;
import java.util.List;

public class ReasonItem {
	
	private Type type;
	
	private List<Reason> reasons=new ArrayList<Reason>();

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Reason> getReasons() {
		return reasons;
	}

	public void setReasons(List<Reason> reasons) {
		this.reasons = reasons;
	}

	public void appendReason(Reason reason){
		this.reasons.add(reason);
	}
	
}
