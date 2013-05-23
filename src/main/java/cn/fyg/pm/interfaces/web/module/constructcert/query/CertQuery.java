package cn.fyg.pm.interfaces.web.module.constructcert.query;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.constructcert.ConstructCertState;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.shared.QuerySpec;
import cn.fyg.pm.infrastructure.tool.DateUtil;

public class CertQuery implements QuerySpec<ConstructCert> {
	
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
	
	private State state;//过滤单据状态
	
	private String orderAttribute;//排序属性
	
	private Type orderType;//排序方式
	
	private Project project;//项目
	
	public CertQuery(){
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	@Override
	public List<Predicate> criterias(CriteriaBuilder builder,
			Root<ConstructCert> from) {
		List<Predicate> criterias=new ArrayList<Predicate>();
		if(this.getProject()!=null){
			criterias.add(builder.equal(from.get("constructKey").get("project"), project));
		}
		if(StringUtils.isNotBlank(this.getNo())){
			criterias.add(builder.like(from.<String>get("no"), "%"+this.getNo().trim()+"%"));
		}
		if(this.getSupplier()!=null){
			if(this.getSupplier().getId()!=null){
				criterias.add(builder.equal(from.get("constructKey").get("supplier"), this.getSupplier()));
			}
		}
		if(this.getCreatedate_beg()!=null){
			criterias.add(builder.greaterThanOrEqualTo(from.<Date>get("createdate"), this.getCreatedate_beg()));
		}
		if(this.getCreatedate_end()!=null){
			Date nextday=DateUtil.nextDay(this.getCreatedate_end());
			criterias.add(builder.lessThanOrEqualTo(from.<Date>get("createdate"),nextday));
		}
		if(this.getState()!=null){
			Path<Object> statePath = from.get("state");
			String mapValue=this.getState().getMapValue();
			mapState(builder, criterias, statePath, mapValue);
			
		}
		return criterias;
	}
	
	private void mapState(CriteriaBuilder builder, List<Predicate> criterias,
			Path<Object> statePath, String mapValue) {
		if(mapValue.equals("ext-all")){
			return;
		}
		if(mapValue.equals("ext-notf")){
			criterias.add(builder.notEqual(statePath, ConstructCertState.finish));
			return;
		}
		criterias.add(builder.equal(statePath,ConstructCertState.valueOf(mapValue)));
		
	}

	@Override
	public List<Order> orders(CriteriaBuilder builder, Root<ConstructCert> from) {
		List<Order> orders=new ArrayList<Order>();
		String[] attrs = this.getOrderAttribute().split("\\.");
		if(attrs.length>0){
			Path<Object> path = from.get(attrs[0]);
			for(int i=1,len=attrs.length;i<len;i++){
				path=path.get(attrs[i]);
			}
			if(this.getOrderType().toString().equals("asc")){
				orders.add(builder.asc(path));
			}else{
				orders.add(builder.desc(path));
			}
		}
		return orders;
	}
	
	

}
