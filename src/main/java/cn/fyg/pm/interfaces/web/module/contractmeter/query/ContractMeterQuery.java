package cn.fyg.pm.interfaces.web.module.contractmeter.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.interfaces.web.shared.query.Qitem;

public class ContractMeterQuery implements QuerySpec<ContractMeter> {
	
	private String no;//编号
	
	private String name;//名称
	
	private Supplier supplier;//供应商
	
	private ContractSpec specialty;//专业分类
	
	private String orderAttribute;//排序属性
	
	private String orderType;//排序方式
	
	private Project project;//项目
	
	
	public ContractMeterQuery() {
		super();
		this.orderAttribute = "no";
		this.orderType = "asc";
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ContractSpec getSpecialty() {
		return specialty;
	}

	public void setSpecialty(ContractSpec specialty) {
		this.specialty = specialty;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	

	@Override
	public List<Predicate> criterias(CriteriaBuilder builder,Root<ContractMeter> from) {
		List<Predicate> criterias=new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(this.getNo())){
			criterias.add(builder.like(from.<String>get("no"), "%"+this.getNo().trim()+"%"));
		}
		
		if(StringUtils.isNotBlank(this.getName())){
			criterias.add(builder.like(from.<String>get("name"), "%"+this.getName().trim()+"%"));
		}
		
		if(this.getSpecialty()!=null){
			criterias.add(builder.equal(from.get("specialty"), this.getSpecialty()));
		}
		
		if(this.getSupplier()!=null){
			if(this.getSupplier().getId()!=null){
				criterias.add(builder.equal(from.get("supplier"), this.getSupplier()));
			}
		}
		
		if(this.getProject()!=null){
			criterias.add(builder.equal(from.get("project"), this.getProject()));
		}
				
		return criterias;
	}

	@Override
	public List<Order> orders(CriteriaBuilder builder, Root<ContractMeter> from) {
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
