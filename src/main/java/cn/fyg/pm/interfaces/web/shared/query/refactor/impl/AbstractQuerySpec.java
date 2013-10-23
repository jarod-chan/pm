package cn.fyg.pm.interfaces.web.shared.query.refactor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import cn.fyg.pm.interfaces.web.shared.query.refactor.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.refactor.QuerySpec;


public abstract class AbstractQuerySpec<T> implements QuerySpec<T> {
	
	private String orderType;//排序方式
	
	private List<Qitem> orderTypeList;//
	
	private String orderAttribute;//排序属性

	private List<Qitem> orderAttributeList;//

	public AbstractQuerySpec() {
		super();
		this.orderType = initOrderType();
		this.orderTypeList=initOrderTypeList();
		this.orderAttribute=initOrderAttribute();
		this.orderAttributeList = new ArrayList<Qitem>();
		initOrderAttributeList(this.orderAttributeList);
	}
	
	public String initOrderType() {
		return "desc";
	}

	public abstract String initOrderAttribute();

	private List<Qitem> initOrderTypeList() {
		List<Qitem> orderTypeList = new ArrayList<Qitem>();
		orderTypeList.add(new Qitem("asc","升序"));
		orderTypeList.add(new Qitem("desc","降序"));
		return orderTypeList;
	}
	
	public abstract void initOrderAttributeList(List<Qitem> attributeList);

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
	
	@Override
	public Specification<T> getSpec() {
		List<Specification<T>> specs=new ArrayList<Specification<T>>();
		doSpec(specs);
		return toSpec(specs);
	};
	

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
	
	public abstract void doSpec(List<Specification<T>> specs);


	@Override
	public Sort getSort(){
		Sort sort=null;
		if(this.getOrderAttribute()!=null){
			if(this.getOrderType().equals("asc")){
				sort=new Sort(new Order(Direction.ASC,this.getOrderAttribute()));
			}else{
				sort=new Sort(new Order(Direction.DESC,this.getOrderAttribute()));
			}
		}
		return sort;
	}

}
