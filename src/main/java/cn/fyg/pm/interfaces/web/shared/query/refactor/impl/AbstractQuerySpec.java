package cn.fyg.pm.interfaces.web.shared.query.refactor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import cn.fyg.pm.interfaces.web.shared.query.refactor.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.refactor.QuerySpec;


public abstract class AbstractQuerySpec<T> implements QuerySpec<T> {
	
	private String orderAttribute;//排序属性
	
	private String orderType;//排序方式
	
	private List<Qitem> orderTypeList;//
	
	private List<Qitem> orderAttributeList;//

	public AbstractQuerySpec() {
		super();
		this.orderAttribute=initOrderAttribute();
		this.orderType = initOrderType();
		this.orderTypeList=initOrderTypeList();
		this.orderAttributeList = new ArrayList<Qitem>();
		initOrderAttributeList(this.orderAttributeList);
	}
	
	private String initOrderType() {
		return "desc";
	}

	public String initOrderAttribute() {
		return "";
	}

	public List<Qitem> initOrderTypeList() {
		List<Qitem> orderTypeList = new ArrayList<Qitem>();
		orderTypeList.add(new Qitem("asc","升序"));
		orderTypeList.add(new Qitem("desc","降序"));
		return orderTypeList;
	}
	
	public void initOrderAttributeList(List<Qitem> attributeList) {
	}

	public String getOrderAttribute() {
		return orderAttribute;
	}
	
	public void setOrderAttribute(String orderAttribute) {
		this.orderAttribute = orderAttribute;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	
	public List<Qitem> getOrderAttributeList(){
		return this.orderAttributeList;
	}

	public List<Qitem> getOrderTypeList(){
		return this.orderTypeList;
	}
	
	public Specification<T> toSpec(List<Specification<T>> specs) {
		Specifications<T> querySpec=null;
		if(!specs.isEmpty()){
			Specification<T> specification = specs.remove(0);
			querySpec=Specifications.where(specification);
			for (Specification<T> spec : specs) {
				querySpec=querySpec.and(spec);
			}
		}
		return querySpec;
	}

	
	public Sort getSort(){
		Sort sort=null;
		if(this.getOrderAttribute()!=null){
			if(this.getOrderType().equals("asc")){
				sort=new Sort(new org.springframework.data.domain.Sort.Order(Direction.ASC,this.getOrderAttribute()));
			}else{
				sort=new Sort(new org.springframework.data.domain.Sort.Order(Direction.DESC,this.getOrderAttribute()));
			}
		}
		return sort;
	}

}
