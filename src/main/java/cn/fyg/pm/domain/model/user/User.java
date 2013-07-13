package cn.fyg.pm.domain.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "pm_user")
public class User {

	@Id
	@Column(name = "key_")
	private String key; // 作为主键

	private String email; // 邮箱

	private String cellphone; // 手机

	private String name; // 用户姓名

	private String password; // 密码

	private String salt; // 加密字串

	@Enumerated(EnumType.STRING)
	private EnabledEnum enabled; // 有效

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public EnabledEnum getEnabled() {
		return enabled;
	}

	public void setEnabled(EnabledEnum enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final User other = (User) object;
		return this.key.equals(other.getKey());
	}

	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

}
