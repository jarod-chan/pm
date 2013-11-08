package cn.fyg.pm.domain.model.identify.userrole;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.identify.role.Fnrole;
import cn.fyg.pm.domain.model.user.User;

@Entity
@Table(name="sh_userfnrole")
public class UserFnrole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_key")
	private User user;
	
	@ManyToOne(targetEntity=Fnrole.class)
	@JoinColumn(name="fnrole_key")
	private Fnrole fnrole;

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

	public Fnrole getFnrole() {
		return fnrole;
	}

	public void setFnrole(Fnrole fnrole) {
		this.fnrole = fnrole;
	}
	
	

}
