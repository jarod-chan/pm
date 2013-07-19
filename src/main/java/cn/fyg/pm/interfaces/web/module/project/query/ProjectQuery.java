package cn.fyg.pm.interfaces.web.module.project.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.interfaces.web.shared.query.Qitem;

public class ProjectQuery implements QuerySpec<Project> {
	
	private String no;//编号
	
	private String name;//名称
	
	private String orderAttribute;//排序属性
	
	private String orderType;//排序方式
	

	public ProjectQuery() {
		super();
		this.orderAttribute = "no";
		this.orderType = "desc";
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public List<Predicate> criterias(CriteriaBuilder builder,Root<Project> from) {
		List<Predicate> criterias=new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(this.getNo())){
			criterias.add(builder.like(from.<String>get("no"), "%"+this.getNo().trim()+"%"));
		}
		
		if(StringUtils.isNotBlank(this.getName())){
			criterias.add(builder.like(from.<String>get("name"), "%"+this.getName().trim()+"%"));
		}
				
		return criterias;
	}

	@Override
	public List<Order> orders(CriteriaBuilder builder, Root<Project> from) {
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
