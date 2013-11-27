package cn.fyg.pm.interfaces.web.module.contract.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.contract.general.ContractSpecs;
import cn.fyg.pm.domain.model.contract.general.ContractType;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.interfaces.web.shared.query.core.Qitem;
import cn.fyg.pm.interfaces.web.shared.query.core.impl.AbstractQuerySpec;

public class ContractQuery extends AbstractQuerySpec<Contract>{
	
	private String no;// 编号

	private String name;// 名称

	private Supplier supplier;// 供应商

	private ContractSpec specialty;// 专业分类

	private Project project;// 项目

	private ContractType contractType;// 合同类型

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

	public ContractType getContractType() {
		return contractType;
	}

	public void setContractType(ContractType contractType) {
		this.contractType = contractType;
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
	public void doSpec(List<Specification<Contract>> specs) {
		if(StringUtils.isNotBlank(this.getNo())){
			specs.add(ContractSpecs.noLike(this.getNo()));
		}
		
		if(StringUtils.isNotBlank(this.getName())){
			specs.add(ContractSpecs.nameLike(name));
		}
		
		if(this.getSpecialty()!=null){
			specs.add(ContractSpecs.isSpecialty(this.getSpecialty()));
		}
		
		if(this.getSupplier()!=null){
			if(this.getSupplier().getId()!=null){
				specs.add(ContractSpecs.withSupplier(this.getSupplier()));
			}
		}
		
		if(this.getProject()!=null){
			specs.add(ContractSpecs.inProject(this.getProject()));
		}
		
		if(this.getContractType()!=null){
			specs.add(ContractSpecs.isContractType(this.getContractType()));
		}
	}
	

	
}
