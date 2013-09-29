package cn.fyg.pm.interfaces.web.module.trace.purchasereq.component;

public class PurchaseReqSmp {
	
	private Long purchaseKeyId;
	
	private Long id;
	
	private String no;	
	
	private String plandate;
	
	private String state;
	
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

	public String getPlandate() {
		return plandate;
	}

	public void setPlandate(String plandate) {
		this.plandate = plandate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
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

	public Long getPurchaseKeyId() {
		return purchaseKeyId;
	}

	public void setPurchaseKeyId(Long purchaseKeyId) {
		this.purchaseKeyId = purchaseKeyId;
	}
	
	

}
