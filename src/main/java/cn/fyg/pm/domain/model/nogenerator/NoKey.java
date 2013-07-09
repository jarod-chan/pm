package cn.fyg.pm.domain.model.nogenerator;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.google.common.base.Objects;

/**
 * 编码模式
 * 联合主键   
 * 1、必须实现Serializable序列化 2、必须提示无参的构造方法 3、必须重写hashCode和equals方法 
 */
@Embeddable
public class NoKey implements Serializable{

	private static final long serialVersionUID = 1L;

	private String sys;//系统编码
	
	private String flag;//标识头
	
	private String pref;//范围
	
	public NoKey(){
		super();
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getPref() {
		return pref;
	}

	public void setPref(String pref) {
		this.pref = pref;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		final NoKey other = (NoKey) object;
		return Objects.equal(this.sys, other.sys)
				&&Objects.equal(this.flag, other.flag)
				&&Objects.equal(this.pref, other.pref);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.sys,this.flag,this.pref);
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
	
}
