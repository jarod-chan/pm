package cn.fyg.pm.domain.model.spmember;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;

/**
 *供应商承包人
 *供应商可以设置对应系统用户，代表承包人
 */
@Entity
@Table(name="pm_spmember")
public class Spmember {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity=Supplier.class)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;	//项目
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_key")
	private User user;	//项目负责人

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
