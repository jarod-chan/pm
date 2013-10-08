package cn.fyg.pm.interfaces.web.shared.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;
import cn.fyg.pm.interfaces.web.shared.query.refactor.Qitem;

public abstract class CommonQuery<T>  implements QuerySpec<T>{
	
	private String no;//编号
	
	private Date createdate_beg;//制单日期开始
	
	private Date createdate_end;//制单日期开始
	
	private String state;
	
	private String orderAttribute;//排序属性
	
	private String orderType;//排序方式
	
	private Project project;//项目
	
	public CommonQuery(){
		this.state="ext-all";
		this.orderAttribute="createdate";
		this.orderType="desc";
	}
	
	
	public List<Qitem> getStateList(){
		ArrayList<Qitem> arrayList = new ArrayList<Qitem>();
		arrayList.add(new Qitem("ext-all","全部"));
		arrayList.add(new Qitem("ext-notf","未完成"));
		arrayList.add(new Qitem("new_","新建"));
		arrayList.add(new Qitem("saved","已保存"));
		arrayList.add(new Qitem("commit","已提交"));
		arrayList.add(new Qitem("finish","已完成"));
		arrayList.add(new Qitem("invalid","已作废"));
		return arrayList;
	}
	
	public List<Qitem> getOrderAttributeList(){
		ArrayList<Qitem> arrayList = new ArrayList<Qitem>();
		arrayList.add(new Qitem("no","编号"));
		arrayList.add(new Qitem("createdate","制单日期"));
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
	public List<Order> orders(CriteriaBuilder builder, Root<T> from) {
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
