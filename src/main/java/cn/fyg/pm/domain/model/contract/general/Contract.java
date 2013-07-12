package cn.fyg.pm.domain.model.contract.general;

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

import cn.fyg.pm.domain.model.contract.ContractRisk;
import cn.fyg.pm.domain.model.contract.ContractSpec;
import cn.fyg.pm.domain.model.contract.ContractState;
import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.BusiCode;

/**
 *合同：
 *项目合同
 */
@Entity
@Table(name="pm_contract")
public class Contract  implements NoPatternUnit {
	
	public static final BusiCode BUSI_CODE = BusiCode.pm_contract;
	
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
	
	@Enumerated(EnumType.STRING)
	private ContractType type;//合同类型
	
	@Enumerated(EnumType.STRING)
	private ContractState state;//合同状态
	
	@Enumerated(EnumType.STRING)
	private ContractSpec specialty;//专业分类
	

	@Temporal(TemporalType.DATE)
	private Date signDate;//签订日期
	
	@Temporal(TemporalType.DATE)
	private Date delvDate; //交付日期
	
	private BigDecimal contractAmt;//合同金额
	
	private BigDecimal dpscale;//首付比例
	
	private BigDecimal finalAmt;//结算金额
	
	private String origins; //来源形式
	
	private String dept;//责任部门
	
	@Enumerated(EnumType.STRING)
	private ContractRisk riskLevel;//风险等级
	
	private String riskPrompt;//风险提示
	
	private String conclusion;//评审结论
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "leader_key")
	private User leader; // 项目负责人
	
	private Long totalCopies;//总份数
	
	private Long saveCopies;//留存份数

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String getNo() {
		return no;
	}
	@Override
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
	public ContractType getType() {
		return type;
	}
	public void setType(ContractType type) {
		this.type = type;
	}
	public ContractState getState() {
		return state;
	}
	public void setState(ContractState state) {
		this.state = state;
	}
	public ContractSpec getSpecialty() {
		return specialty;
	}
	public void setSpecialty(ContractSpec specialty) {
		this.specialty = specialty;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Date getDelvDate() {
		return delvDate;
	}
	public void setDelvDate(Date delvDate) {
		this.delvDate = delvDate;
	}
	public BigDecimal getContractAmt() {
		return contractAmt;
	}
	public void setContractAmt(BigDecimal contractAmt) {
		this.contractAmt = contractAmt;
	}
	public BigDecimal getDpscale() {
		return dpscale;
	}
	public void setDpscale(BigDecimal dpscale) {
		this.dpscale = dpscale;
	}
	public BigDecimal getFinalAmt() {
		return finalAmt;
	}
	public void setFinalAmt(BigDecimal finalAmt) {
		this.finalAmt = finalAmt;
	}
	public String getOrigins() {
		return origins;
	}
	public void setOrigins(String origins) {
		this.origins = origins;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public ContractRisk getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(ContractRisk riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getRiskPrompt() {
		return riskPrompt;
	}
	public void setRiskPrompt(String riskPrompt) {
		this.riskPrompt = riskPrompt;
	}
	public String getConclusion() {
		return conclusion;
	}
	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}
	public User getLeader() {
		return leader;
	}
	public void setLeader(User leader) {
		this.leader = leader;
	}
	public Long getTotalCopies() {
		return totalCopies;
	}
	public void setTotalCopies(Long totalCopies) {
		this.totalCopies = totalCopies;
	}
	public Long getSaveCopies() {
		return saveCopies;
	}
	public void setSaveCopies(Long saveCopies) {
		this.saveCopies = saveCopies;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("D");
		nokey.setFlag("HT");
		String projectNo=this.project.getNo();
		String[] noParts=projectNo.split("-");
		nokey.setPref(noParts[2]+noParts[3]+this.type.getCode());
		Long limit=Long.valueOf(9999);
	    return new NoPattern(nokey,limit);
	}
	
}
