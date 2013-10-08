package cn.fyg.pm.interfaces.web.module.supplier.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.supplier.CreditRank;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.supplier.Supptype;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.interfaces.web.shared.query.refactor.Qitem;

public class SupplierQuery implements QuerySpec<Supplier> {
	
	private String no;//编号
	
	private String name;//名称
	
	private CreditRank creditRank;//信用等级
	
	private String orderAttribute;//排序属性
	
	private String orderType;//排序方式
	
	private Supptype type;	// 类型
	
	public SupplierQuery() {
		super();
		this.orderAttribute = "no";
		this.orderType = "asc";
	}
	
	public CreditRank[] getCreditRankList(){
		return CreditRank.values();
	}

	public List<Qitem> getOrderAttributeList(){
		ArrayList<Qitem> arrayList = new ArrayList<Qitem>();
		arrayList.add(new Qitem("no","编号"));
		return arrayList;
	}
	
	public List<Qitem> getOrderTypeList(){
		ArrayList<Qitem> arrayList = new ArrayList<Qitem>();
		arrayList.add(new Qitem("asc","升序"));
		arrayList.add(new Qitem("desc","降序"));
		return arrayList;
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Supptype getType() {
		return type;
	}

	public void setType(Supptype type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CreditRank getCreditRank() {
		return creditRank;
	}

	public void setCreditRank(CreditRank creditRank) {
		this.creditRank = creditRank;
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


	@Override
	public List<Predicate> criterias(CriteriaBuilder builder,Root<Supplier> from) {
		List<Predicate> criterias=new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(this.getNo())){
			criterias.add(builder.like(from.<String>get("no"), "%"+this.getNo().trim()+"%"));
		}
		
		if(StringUtils.isNotBlank(this.getName())){
			criterias.add(builder.like(from.<String>get("name"), "%"+this.getName().trim()+"%"));
		}
		
		if(this.getCreditRank()!=null){
			criterias.add(builder.equal(from.get("creditRank"), this.getCreditRank()));
		}
		
		if(this.getType()!=null){
			criterias.add(builder.equal(from.get("type"), this.getType()));
		}
	
		return criterias;
	}

	@Override
	public List<Order> orders(CriteriaBuilder builder, Root<Supplier> from) {
		List<Order> orders=new ArrayList<Order>();
		String[] attrs = this.getOrderAttribute().split("\\.");
		if(attrs.length>0){
			Path<Object> path = from.get(attrs[0]);
			for(int i=1,len=attrs.length;i<len;i++){
				path=path.get(attrs[i]);
			}
			if(this.getOrderType().equals("asc")){
				orders.add(builder.asc(path));
			}else{
				orders.add(builder.desc(path));
			}
		}
		return orders;
	}

}
