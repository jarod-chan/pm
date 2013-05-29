package cn.fyg.pm.domain.model.construct.constructkey;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.contract.Contract;
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
	private long id;//id
	
	private String no;//编号
	
	@ManyToOne(targetEntity=Contract.class)
	@JoinColumn(name="contract_id")
	private Contract contract;//合同
	
	@ManyToOne(targetEntity=Project.class)
	@JoinColumn(name="project_id")
	private Project project;//项目
	
	@ManyToOne(targetEntity=Supplier.class)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;  //供应商
	

	
	@Transient
	private String reason;//原因
	
	@Transient
	@ManyToOne(targetEntity=ConstructCont.class,fetch=FetchType.LAZY)
	private ConstructCont constructCont;
	
	@Transient
	@ManyToOne(targetEntity=ConstructCert.class,fetch=FetchType.LAZY)
	private ConstructCert constructCert;
	
	

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setConstructCert(ConstructCert constructCert) {
		this.constructCert = constructCert;
	}

	public ConstructCont getConstructCont() {
		return constructCont;
	}

	public void setConstructCont(ConstructCont constructCont) {
		this.constructCont = constructCont;
	}

	public ConstructCert getConstructCert() {
		return constructCert;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
