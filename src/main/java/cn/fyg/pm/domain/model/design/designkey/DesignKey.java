package cn.fyg.pm.domain.model.design.designkey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.project.Project;

/**
 *设计问题变更线索
 *保存一条线索跟踪设计问题
 */
@Entity
@Table(name="pm_designkey")
public class DesignKey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id
	
	@ManyToOne(targetEntity=Project.class)
	@JoinColumn(name="project_id")
	private Project project;//项目


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
