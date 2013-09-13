package cn.fyg.pm.domain.model.purchase.purchasereq.item;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;

/**
 *采购申请单明细
 */
@Entity
@Table(name="pm_purchasereqitem")
public class PurchaseReqItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id

	private Long sn;//序号
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="purchasereq_id")
	private PurchaseReq purchaseReq;
	
	private String metername;//材料名称
	
	private String spec;//规格型号，技术指标
	
	private String unit;//单位
	
	private BigDecimal numb;//数量
	
	private String brand;//推荐品牌
	
	@Enumerated(EnumType.STRING)
	private UptypeEnum uptype;//关联单据类型
	
	private Long upid;//关联单据id
	
	private String upno;//关联单据类型
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSn() {
		return sn;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public PurchaseReq getPurchaseReq() {
		return purchaseReq;
	}

	public void setPurchaseReq(PurchaseReq purchaseReq) {
		this.purchaseReq = purchaseReq;
	}


	public String getMetername() {
		return metername;
	}

	public void setMetername(String metername) {
		this.metername = metername;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}


	public BigDecimal getNumb() {
		return numb;
	}

	public void setNumb(BigDecimal numb) {
		this.numb = numb;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getUpid() {
		return upid;
	}

	public void setUpid(Long upid) {
		this.upid = upid;
	}

	public String getUpno() {
		return upno;
	}

	public void setUpno(String upno) {
		this.upno = upno;
	}

	public UptypeEnum getUptype() {
		return uptype;
	}

	public void setUptype(UptypeEnum uptype) {
		this.uptype = uptype;
	}	
	
}
