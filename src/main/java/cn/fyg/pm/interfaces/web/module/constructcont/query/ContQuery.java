package cn.fyg.pm.interfaces.web.module.constructcont.query;


import java.util.Date;

import cn.fyg.pm.domain.model.supplier.Supplier;

public class ContQuery {
	
	public enum Type{
		asc,
		desc;
	}
	
	public enum State{
		all("全部","ext-all"),
		notfinish("未完成","ext-notf"),
		new_("新建","new_"),
		saved("已保存","saved"),
		commit("已提交","commit"),
		finish("已完成","finish");
		
		private final String name;
		private final String mapValue;
		private State(String name,String mapValue){
			this.name=name;
			this.mapValue=mapValue;
		}
		public String getName() {
			return name;
		}
		public String getMapValue() {
			return mapValue;
		}
		
	}
	
	private String no;//编号
	
	private Supplier supplier;//制单人
	
	private Date createdate_beg;//制单日期开始
	
	private Date createdate_end;//制单日期开始
	
	private State state;
	
	private String orderAttribute;//排序属性
	
	private Type orderType;//排序方式
	
	public ContQuery(){
		this.state=State.notfinish;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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
