package cn.fyg.pm.domain.model.purchase.purchasekey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.contract.Contract;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;

/**
 *采购线索
 *用于关联采购申请和采购签证
 */
@Entity
@Table(name="pm_purchasekey")
public class PurchaseKey {
	
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
