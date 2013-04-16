package cn.fyg.pm.domain.constructcert;

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

import cn.fyg.pm.domain.constructkey.ConstructKey;
import cn.fyg.pm.domain.user.User;

/**
 *施工签证单
 */
@Entity
@Table(name="pm_constructcert")
public class ConstructCert {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;//id
	
	private String no;//编号
	
	@ManyToOne(targetEntity=ConstructKey.class)
	@JoinColumn(name="constructkey_id")
	private ConstructKey constructKey;//施工签证线索
	
	@Enumerated(EnumType.STRING)
	private ConstructCertState state;//状态

	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="creater_key")
	private User creater;//制单人
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;//制单时间
	
	@OneToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.ALL},
			targetEntity = ConstructCertItem.class,
			orphanRemoval=true)
	@OrderBy("sn ASC")
	@JoinColumn(name="constructcert_id")
	private List<ConstructCertItem> ConstructCertItems;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<ConstructCertItem> getConstructCertItems() {
		return ConstructCertItems;
	}

	public void setConstructCertItems(List<ConstructCertItem> constructCertItems) {
		ConstructCertItems = constructCertItems;
	}
	
	
}
