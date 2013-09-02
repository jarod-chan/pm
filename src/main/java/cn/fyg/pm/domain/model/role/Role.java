package cn.fyg.pm.domain.model.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pm_role")
public class Role {
	
	@Id
	@Column(name="key_")
	private String key; //作为主键
	
	private String name; //项目名称
	
	@Column(unique=true)
	private Long sn;//序号
	
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
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

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	

}
