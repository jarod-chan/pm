package cn.fyg.pm.domain.model.construct.constructkey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

/**
 *施工签证线索
 */
@Entity
@Table(name="pm_constructkey")
public class ConstructKey {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id
	
	@ManyToOne(targetEntity=Contract.class)
	@JoinColumn(name="contract_id")
	private Contract contract;//合同
	
	@ManyToOne(targetEntity=Project.class)
	@JoinColumn(name="project_id")
	private Project project;//项目
	
	@ManyToOne(targetEntity=Supplier.class)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;  //供应商
	
	private Long constructcert_id;//签证单id 

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConstructcert_id() {
		return constructcert_id;
	}

	public void setConstructcert_id(Long constructcert_id) {
		this.constructcert_id = constructcert_id;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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
	

	
	
}
