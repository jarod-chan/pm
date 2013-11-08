package cn.fyg.pm.domain.model.identify.rolepm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.identify.permission.Permission;
import cn.fyg.pm.domain.model.identify.role.Fnrole;

@Entity
@Table(name="sh_rolepm")
public class Rolepm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity=Fnrole.class)
	@JoinColumn(name="fnrole_key")
	private Fnrole fnrole;

	@ManyToOne(targetEntity=Fnrole.class)
	@JoinColumn(name="permission_key")
	private Permission permission;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Fnrole getFnrole() {
		return fnrole;
	}

	public void setFnrole(Fnrole fnrole) {
		this.fnrole = fnrole;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	
}
