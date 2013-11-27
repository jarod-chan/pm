package cn.fyg.pm.interfaces.web.module.contractmeter.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeterSpecs;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.interfaces.web.shared.query.core.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.core.impl.AbstractQuerySpec;

public class ContractMeterQuery  extends AbstractQuerySpec<ContractMeter>{
	
	private String no;//编号
	
	private String name;//名称
	
	private Supplier supplier;//供应商
	
	private ContractSpec specialty;//专业分类
	
	private Project project;//项目
	
	
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
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	@Override
	public String initOrderAttribute(){
		return "no";
	}
	
	@Override
	public void initOrderAttributeList(List<Qitem> attributeList) {
		attributeList.add(new Qitem("no","编号"));
	}
	
	@Override
	public void doSpec(java.util.List<org.springframework.data.jpa.domain.Specification<ContractMeter>> specs) {
		if(StringUtils.isNotBlank(this.getNo())){
			specs.add(ContractMeterSpecs.noLike(this.getNo()));
		}
		
		if(StringUtils.isNotBlank(this.getName())){
			specs.add(ContractMeterSpecs.nameLike(this.getName()));
		}
		
		if(this.getSpecialty()!=null){
			specs.add(ContractMeterSpecs.isSpecialty(this.getSpecialty()));
		}
		
		if(this.getSupplier()!=null){
			if(this.getSupplier().getId()!=null){
				specs.add(ContractMeterSpecs.withSupplier(this.getSupplier()));
			}
		}
		
		if(this.getProject()!=null){
			specs.add(ContractMeterSpecs.inProject(project));
		}
	}
	

}
