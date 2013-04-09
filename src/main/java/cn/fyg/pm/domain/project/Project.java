package cn.fyg.pm.domain.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.fyg.pm.domain.user.User;

/**
 * 项目：
 * 项目基础信息
 */
@Entity
@Table(name="pm_project")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	//项目id
	private String no;		//编号
	private String name;	//项目名称
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_key")
	private User user;	//项目负责人

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	
	
	@Override
	public String toString() {
		 return ToStringBuilder.reflectionToString(this);
	}

}
