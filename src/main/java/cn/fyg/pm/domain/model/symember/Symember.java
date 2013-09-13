package cn.fyg.pm.domain.model.symember;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;

/**
 *项目成员
 */
@Entity
@Table(name="pm_symember")
public class Symember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_key")
	private User user;	//项目
	
	@ManyToOne(targetEntity=Role.class)
	@JoinColumn(name="role_key")
	private Role role;//项目角色

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
