package cn.fyg.pm.interfaces.web.module.trace.designnoti.component;

import cn.fyg.pm.domain.shared.CommonEnum;

public class DesignNotiSmp {
	
	private Long id;
	
	private String no;	
	
	private String busino;
	
	private CommonEnum state;
	
	private String state_name;
	
	private String creater_name;
	
	private String createdate;

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

	public CommonEnum getState() {
		return state;
	}

	public void setState(CommonEnum state) {
		this.state = state;
	}

	public String getCreater_name() {
		return creater_name;
	}

	public void setCreater_name(String creater_name) {
		this.creater_name = creater_name;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getBusino() {
		return busino;
	}

	public void setBusino(String busino) {
		this.busino = busino;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	
	

}
