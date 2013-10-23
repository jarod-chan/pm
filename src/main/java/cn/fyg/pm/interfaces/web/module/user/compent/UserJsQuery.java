package cn.fyg.pm.interfaces.web.module.user.compent;

import cn.fyg.pm.domain.model.user.EnabledEnum;

public class UserJsQuery {
	
	private String key;	
	
	private String name;
	
	private EnabledEnum enabled;
	
	private int page;//页码

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EnabledEnum getEnabled() {
		return enabled;
	}

	public void setEnabled(EnabledEnum enabled) {
		this.enabled = enabled;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	

}
