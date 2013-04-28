package cn.fyg.pm.domain.model.constructcont;

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

import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.user.User;

/**
 *施工联系单
 */
@Entity
@Table(name="pm_constructcont")
public class ConstructCont {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;//id
	
	private String no;//编号
	
	@ManyToOne(targetEntity=ConstructKey.class,cascade={CascadeType.ALL})
	@JoinColumn(name="constructkey_id")
	private ConstructKey constructKey;//施工签证线索
	
	private String reason;//原因
	
	@Enumerated(EnumType.STRING)
	private ConstructContState state;//状态
	
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
	
	@OneToMany(mappedBy = "constructCont",
			fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL},
			targetEntity = ConstructContItem.class,
			orphanRemoval=true)
	@OrderBy("sn ASC")
	private List<ConstructContItem> constructContItems;

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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ConstructContState getState() {
		return state;
	}

	public void setState(ConstructContState state) {
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

	public List<ConstructContItem> getConstructContItems() {
		return constructContItems;
	}

	public void setConstructContItems(List<ConstructContItem> constructContItems) {
		this.constructContItems = constructContItems;
	}

	public ConstructKey getConstructKey() {
		return constructKey;
	}

	public void setConstructKey(ConstructKey constructKey) {
		this.constructKey = constructKey;
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
	
	
}
