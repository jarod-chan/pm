package cn.fyg.pm.interfaces.web.shared.flow;

import java.util.Date;

public class CheckBean {

	private String userName;// 审批人姓名

	private Date date;// 日期

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
