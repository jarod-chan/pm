package cn.fyg.pm.domain.model.design.designnoti;

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

import cn.fyg.pm.domain.model.design.designkey.DesignKey;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.BusiCode;
import cn.fyg.pm.domain.shared.CommonNoPatternUnit;

/**
 *设计问题通知单
 *工程部向采购部提交问题，采购部使用技术联系单回复
 */
@Entity
@Table(name="pm_designnoti")
public class DesignNoti extends CommonNoPatternUnit{
	
	public static final BusiCode BUSI_CODE=BusiCode.pm_techcont;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id
	
	private String no;//编号
	
	private String busino;//业务编号
	
	@ManyToOne(targetEntity=DesignKey.class,cascade={CascadeType.ALL})
	@JoinColumn(name="designkey_id")
	private DesignKey designKey;//设计线索
	
	private String graphno;//图号
	
	private String postion;//变更部位
	
	@Enumerated(EnumType.STRING)
	private DesignNotiState state;//状态
	
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
	
	@OneToMany(mappedBy = "designNoti",
			fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL},
			targetEntity = DesignNotiItem.class,
			orphanRemoval=true)
	@OrderBy("sn ASC")
	private List<DesignNotiItem> designNotiItems=new ArrayList<DesignNotiItem>();

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

	public DesignKey getDesignKey() {
		return designKey;
	}

	public void setDesignKey(DesignKey designKey) {
		this.designKey = designKey;
	}

	public String getGraphno() {
		return graphno;
	}

	public void setGraphno(String graphno) {
		this.graphno = graphno;
	}

	public String getPostion() {
		return postion;
	}

	public void setPostion(String postion) {
		this.postion = postion;
	}

	public DesignNotiState getState() {
		return state;
	}

	public void setState(DesignNotiState state) {
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

	public List<DesignNotiItem> getDesignNotiItems() {
		return designNotiItems;
	}

	public void setDesignNotiItems(List<DesignNotiItem> designNotiItems) {
		this.designNotiItems = designNotiItems;
	}
	
	

}
