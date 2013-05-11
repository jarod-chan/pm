package cn.fyg.pm.interfaces.web.shared.message;

public enum Level {
	
	info("信息"),error("警告");
	
	private String name;
	
	private Level(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
