package cn.fyg.pm.domain.contract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.project.Project;
import cn.fyg.pm.domain.supplier.Supplier;

/**
 *合同：
 *项目合同
 */
@Entity
@Table(name="pm_contract")
public class Contract {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	//id
	private String no;	//编号
	private String name;//项目名称
	@ManyToOne(targetEntity=Project.class)
	@JoinColumn(name="project_id")
	private Project project;	//项目
	@ManyToOne(targetEntity=Supplier.class)
	@JoinColumn(name="supplier_id")
	private Supplier supplier;  //供应商
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
