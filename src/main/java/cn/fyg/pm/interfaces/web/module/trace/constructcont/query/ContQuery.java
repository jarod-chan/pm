package cn.fyg.pm.interfaces.web.module.trace.constructcont.query;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContSpecs;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.refactor.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.refactor.impl.AbstractQuerySpec;

public class ContQuery extends AbstractQuerySpec<ConstructCont>{
	
	private String no;//编号
	
	private Supplier supplier;//供应商
	
	private Date createdate_beg;//制单日期开始
	
	private Date createdate_end;//制单日期开始
	
	private ContractSpec specialty;//专业分类
	
	private String state;
	
	private Project project;//项目
	
	public ContractSpec[] getSpecialtyList(){
		return ContractSpec.values();
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
	
	
	@Override
	public String initOrderAttribute() {
		this.state="ext-all";
		return "createdate";
	}

	@Override
	public void initOrderAttributeList(List<Qitem> attributeList) {
		attributeList.add(new Qitem("no","编号"));
		attributeList.add(new Qitem("constructKey.supplier.id","施工承包方"));
		attributeList.add(new Qitem("createdate","制单日期"));
	}
	
	@Override
	public void doSpec(List<Specification<ConstructCont>> specs) {
		if(this.getProject()!=null){
			specs.add(ConstructContSpecs.inProject(project));
		}
		if(StringUtils.isNotBlank(this.getNo())){
			specs.add(ConstructContSpecs.noLike(this.getNo().trim()));
		}
		if(this.getSupplier()!=null){
			if(this.getSupplier().getId()!=null){
				specs.add(ConstructContSpecs.withSupplier(this.getSupplier()));
			}
		}
		if(this.getCreatedate_beg()!=null){
			specs.add(ConstructContSpecs.createAfterDate(this.getCreatedate_beg()));
		}
		if(this.getCreatedate_end()!=null){
			Date nextday=DateUtil.nextDay(this.getCreatedate_end());
			specs.add(ConstructContSpecs.createBeforeDate(nextday));
		}
		if(this.getSpecialty()!=null){
			specs.add(ConstructContSpecs.isSpecialty(this.getSpecialty()));
		}
		if(this.getState()!=null){
			mapState(specs, this.getState());
			
		}
	}
	
	private void mapState(List<Specification<ConstructCont>> specs,
			String stateValue) {
		if(stateValue.equals("ext-all")){
			return;
		}
		if(stateValue.equals("ext-notf")){
			specs.add(ConstructContSpecs.notState(ConstructContState.finish));
			return;
		}
		specs.add(ConstructContSpecs.isState(ConstructContState.valueOf(stateValue)));
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

	public ContractSpec getSpecialty() {
		return specialty;
	}

	public void setSpecialty(ContractSpec specialty) {
		this.specialty = specialty;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
