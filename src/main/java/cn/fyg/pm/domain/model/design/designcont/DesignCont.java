package cn.fyg.pm.domain.model.design.designcont;

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

import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.domain.model.design.designcont.reason.Reason;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.domain.shared.CommonNoPatternUnit;

@Entity
@Table(name="pm_designcont")
public class DesignCont  extends CommonNoPatternUnit{
	
	public static final BusiCode BUSI_CODE=BusiCode.pm_designcont;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id
	
	private String no;//编号
	
	private String busino;//业务编号
	
//	@ManyToOne(targetEntity=DesignKey.class)
//	@JoinColumn(name="designkey_id")
//	private DesignKey designKey;//设计线索
	
	@ManyToOne(targetEntity=Contract.class)
	@JoinColumn(name="contract_id")
	private Contract contract;//合同
	
	@Enumerated(EnumType.STRING)
	private Reason reason;
	
	@Enumerated(EnumType.STRING)
	private DesignContState state;//状态

	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="leader_key")
	private User leader;//当前项目负责人
	
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
	
	@OneToMany(mappedBy = "designCont",
			fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL},
			targetEntity = DesignContItem.class,
			orphanRemoval=true)
	@OrderBy("sn ASC")
	private List<DesignContItem> designContItems=new ArrayList<DesignContItem>();

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

	public String getBusino() {
		return busino;
	}

	public void setBusino(String busino) {
		this.busino = busino;
	}

//	public DesignKey getDesignKey() {
//		return designKey;
//	}
//
//	public void setDesignKey(DesignKey designKey) {
//		this.designKey = designKey;
//	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public DesignContState getState() {
		return state;
	}

	public void setState(DesignContState state) {
		this.state = state;
	}

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
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

	public List<DesignContItem> getDesignContItems() {
		return designContItems;
	}

	public void setDesignContItems(List<DesignContItem> designContItems) {
		this.designContItems = designContItems;
	}

}
