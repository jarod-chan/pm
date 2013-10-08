package cn.fyg.pm.interfaces.web.shared.query.refactor;

/**
 *选择项目
 */
public class Qitem {
	
	private String value;
	
	private String name;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Qitem(String value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
	
	
}
