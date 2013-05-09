package cn.fyg.pm.domain.model.project;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.fyg.pm.domain.model.user.User;

/**
 * 项目： 项目基础信息
 */
@Entity
@Table(name = "pm_project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 项目id
	private String no; // 编号
	private String name; // 项目名称
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "leader_key")
	private User leader; // 项目负责人

	@Enumerated(EnumType.STRING)
	private ProjectStateEnum state;// 项目状态

	private String inveplan;// 项目投资计划批文
	private String landuseRight;// 土地使用权证
	private String landusePermit;// 用地许可证
	private String projectPlan;// 工程规划许可证
	private String projectCnut;// 工程施工许可证
	private String perSale;// 商品房预售许可证
	private String completionConfirm;// 竣工验收规划确认书
	private String completionBackup;// 竣工验收备案
	
	@Temporal(TemporalType.DATE)
	private Date begDate;// 开工日期
	
	@Temporal(TemporalType.DATE)
	private Date endDate;// 竣工日期
	
	private String location;// 位置
	private BigDecimal stock;// 所占股份
	private BigDecimal totalinve;// 总投资

	public ProjectStateEnum getState() {
		return state;
	}

	public void setState(ProjectStateEnum state) {
		this.state = state;
	}

	public String getInveplan() {
		return inveplan;
	}

	public void setInveplan(String inveplan) {
		this.inveplan = inveplan;
	}

	public String getLanduseRight() {
		return landuseRight;
	}

	public void setLanduseRight(String landuseRight) {
		this.landuseRight = landuseRight;
	}

	public String getLandusePermit() {
		return landusePermit;
	}

	public void setLandusePermit(String landusePermit) {
		this.landusePermit = landusePermit;
	}

	public String getProjectPlan() {
		return projectPlan;
	}

	public void setProjectPlan(String projectPlan) {
		this.projectPlan = projectPlan;
	}

	public String getProjectCnut() {
		return projectCnut;
	}

	public void setProjectCnut(String projectCnut) {
		this.projectCnut = projectCnut;
	}

	public String getPerSale() {
		return perSale;
	}

	public void setPerSale(String perSale) {
		this.perSale = perSale;
	}

	public String getCompletionConfirm() {
		return completionConfirm;
	}

	public void setCompletionConfirm(String completionConfirm) {
		this.completionConfirm = completionConfirm;
	}

	public String getCompletionBackup() {
		return completionBackup;
	}

	public void setCompletionBackup(String completionBackup) {
		this.completionBackup = completionBackup;
	}

	public Date getBegDate() {
		return begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public BigDecimal getTotalinve() {
		return totalinve;
	}

	public void setTotalinve(BigDecimal totalinve) {
		this.totalinve = totalinve;
	}

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

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
