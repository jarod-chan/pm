package cn.fyg.pm.domain.model.identify.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sh_fnrole")
public class Fnrole {
	
	@Id
	@Column(name="key_")
	private String key; //作为主键
	
	private String name; //项目名称

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
	
	

}
