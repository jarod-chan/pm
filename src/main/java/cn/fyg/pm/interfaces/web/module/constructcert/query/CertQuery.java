package cn.fyg.pm.interfaces.web.module.constructcert.query;


import java.util.Date;

import cn.fyg.pm.domain.model.supplier.Supplier;

public class CertQuery {
	
	public enum Type{
		asc,
		desc;
	}
	
	private String no;//编号
	
	private Supplier supplier;//制单人
	
	private Date createdate_beg;//制单日期开始
	
	private Date createdate_end;//制单日期开始
	
	private Boolean filterFinish;//过滤完成单据
	
	private String orderAttribute;//排序属性
	
	private Type orderType;//排序方式
	
	public CertQuery(){
		this.filterFinish=Boolean.TRUE;
		this.orderAttribute="createdate";
		this.orderType=Type.desc;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Date getCreatedate_beg() {
		return createdate_beg;
	}

	public void setCreatedate_beg(Date createdate_beg) {
		this.createdate_beg = createdate_beg;
	}

	public Date getCreatedate_end() {
		return createdate_end;
	}

	public void setCreatedate_end(Date createdate_end) {
		this.createdate_end = createdate_end;
	}

	public Boolean getFilterFinish() {
		return filterFinish;
	}

	public void setFilterFinish(Boolean filterFinish) {
		this.filterFinish = filterFinish;
	}

	public String getOrderAttribute() {
		return orderAttribute;
	}

	public void setOrderAttribute(String orderAttribute) {
		this.orderAttribute = orderAttribute;
	}

	public Type getOrderType() {
		return orderType;
	}

	public void setOrderType(Type orderType) {
		this.orderType = orderType;
	}
	
	

}
