package cn.fyg.pm.interfaces.web.module.supplier.component;

import cn.fyg.pm.domain.model.supplier.Supptype;

public class SupplierJsQuery {
	
	private String no;	
	
	private String name;
	
	private Supptype[] types;
	
	private int page;//页码

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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Supptype[] getTypes() {
		return types;
	}

	public void setTypes(Supptype[] types) {
		this.types = types;
	}

	
}
