package cn.fyg.pm.domain.model.workflow.opinion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.fyg.pm.domain.shared.BusiCode;

@Entity
@Table(name="pm_opinionitem")
public class OpinionItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private BusiCode busiCode;//业务编码，用来区分不同单据
	
	private Long businessId;//业务id，唯一确定一个id
	
	private Long itemId;//明细id
	
	private Long itemSn;//明细序号
	
	@Enumerated(EnumType.STRING)
	private ResultEnum result;//是否同意
	
	@Column(length=256)
	private String content;//审批意见

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BusiCode getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(BusiCode busiCode) {
		this.busiCode = busiCode;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getItemSn() {
		return itemSn;
	}

	public void setItemSn(Long itemSn) {
		this.itemSn = itemSn;
	}

	public ResultEnum getResult() {
		return result;
	}

	public void setResult(ResultEnum result) {
		this.result = result;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
