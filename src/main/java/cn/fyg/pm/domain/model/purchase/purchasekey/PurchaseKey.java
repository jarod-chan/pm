package cn.fyg.pm.domain.model.purchase.purchasekey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.project.Project;

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
	
	@ManyToOne(targetEntity=Project.class)
	@JoinColumn(name="project_id")
	private Project project;//项目

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	

	

}
