package cn.fyg.pm.domain.model.purchase.purchasecert;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.shared.EntityItem;

@Entity
@Table(name="pm_purchasecertitem")
public class PurchaseCertItem implements EntityItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id

	private Long sn;//序号
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="purchasecert_id")
	private PurchaseCert purchaseCert;//材料签证
	
	private String metername;//材料名称
	
	private String spec;//规格型号，技术指标
	
	private String unit;//单位
	
	private BigDecimal numb;//数量
	
	private String brand;//品牌
	
	private BigDecimal price;//单价
	
	private BigDecimal amount;//金额

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

	public PurchaseCert getPurchaseCert() {
		return purchaseCert;
	}

	public void setPurchaseCert(PurchaseCert purchaseCert) {
		this.purchaseCert = purchaseCert;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	

}
