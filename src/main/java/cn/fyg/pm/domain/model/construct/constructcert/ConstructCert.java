package cn.fyg.pm.domain.model.construct.constructcert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.BusiCode;

/**
 * 施工签证单
 */
@Entity
@Table(name = "pm_constructcert")
public class ConstructCert {

	public static final BusiCode BUSI_CODE = BusiCode.pm_constructcert;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;// id

	private String no;// 编号

	@ManyToOne(targetEntity = ConstructKey.class)
	@JoinColumn(name = "constructkey_id")
	private ConstructKey constructKey;// 施工签证线索

	private String reason;// 原因

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "leader_key")
	private User leader;// 当前项目负责人

	@Enumerated(EnumType.STRING)
	private ConstructCertState state;// 状态

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "creater_key")
	private User creater;// 制单人

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;// 制单日期

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "signer_key")
	private User signer;// 签发人

	@Temporal(TemporalType.TIMESTAMP)
	private Date signdate;// 签发日期
	
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "settler_key")
	private User settler;// 结算人

	@Temporal(TemporalType.TIMESTAMP)
	private Date settledate;// 结算日期
	
	private BigDecimal tolsum;//总金额

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, targetEntity = ConstructCertItem.class, orphanRemoval = true)
	@OrderBy("sn ASC")
	@JoinColumn(name = "constructcert_id")
	private List<ConstructCertItem> ConstructCertItems=new ArrayList<ConstructCertItem>();

	public User getSettler() {
		return settler;
	}

	public void setSettler(User settler) {
		this.settler = settler;
	}

	public Date getSettledate() {
		return settledate;
	}

	public void setSettledate(Date settledate) {
		this.settledate = settledate;
	}

	public Long getId() {
		return id;
	}

	public BigDecimal getTolsum() {
		return tolsum;
	}

	public void setTolsum(BigDecimal tolsum) {
		this.tolsum = tolsum;
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

	public ConstructKey getConstructKey() {
		return constructKey;
	}

	public void setConstructKey(ConstructKey constructKey) {
		this.constructKey = constructKey;
	}

	public ConstructCertState getState() {
		return state;
	}

	public void setState(ConstructCertState state) {
		this.state = state;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	public User getSigner() {
		return signer;
	}

	public void setSigner(User signer) {
		this.signer = signer;
	}

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public List<ConstructCertItem> getConstructCertItems() {
		return ConstructCertItems;
	}

	public void setConstructCertItems(List<ConstructCertItem> constructCertItems) {
		ConstructCertItems = constructCertItems;
	}

}
