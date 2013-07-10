package cn.fyg.pm.domain.model.purchase.purchasecert;

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

import cn.fyg.pm.domain.model.purchase.purchasekey.PurchaseKey;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.domain.shared.CommonNoPatternUnit;

@Entity
@Table(name="pm_purchasecert")
public class PurchaseCert extends CommonNoPatternUnit{
	
	public static final BusiCode BUSI_CODE = BusiCode.pm_purchasecert;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id
	
	private String no;//编号
	
	@ManyToOne(targetEntity=PurchaseKey.class)
	@JoinColumn(name="purchasekey_id")
	private PurchaseKey purchaseKey;
	
	@Enumerated(EnumType.STRING)
	private PurchaseCertState state;//状态
	
	@Temporal(TemporalType.DATE)
	private Date plandate;//计划进场时间
	
	private String descrp;//采购部意见
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="leader_key")
	private User leader;//当前项目负责人
	
	private BigDecimal tolsum;//总金额
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="creater_key")
	private User creater;//制单人
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;//制单日期
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="signer_key")
	private User signer;//签发人
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date signdate;//签发日期
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="receiver_key")
	private User receiver;//接收人
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date receivedate;//接收日期
	
	@OneToMany(mappedBy = "purchaseCert",
			fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL},
			targetEntity = PurchaseCertItem.class,
			orphanRemoval=true)
	@OrderBy("sn ASC")
	private List<PurchaseCertItem> purchaseCertItems=new ArrayList<PurchaseCertItem>();

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

	public PurchaseKey getPurchaseKey() {
		return purchaseKey;
	}

	public void setPurchaseKey(PurchaseKey purchaseKey) {
		this.purchaseKey = purchaseKey;
	}

	public PurchaseCertState getState() {
		return state;
	}

	public void setState(PurchaseCertState state) {
		this.state = state;
	}

	public Date getPlandate() {
		return plandate;
	}

	public void setPlandate(Date plandate) {
		this.plandate = plandate;
	}

	public String getDescrp() {
		return descrp;
	}

	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	public BigDecimal getTolsum() {
		return tolsum;
	}

	public void setTolsum(BigDecimal tolsum) {
		this.tolsum = tolsum;
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

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Date getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public List<PurchaseCertItem> getPurchaseCertItems() {
		return purchaseCertItems;
	}

	public void setPurchaseCertItems(List<PurchaseCertItem> purchaseCertItems) {
		this.purchaseCertItems = purchaseCertItems;
	}

	
}
