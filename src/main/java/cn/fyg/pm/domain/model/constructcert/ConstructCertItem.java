package cn.fyg.pm.domain.model.constructcert;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pm_constructcertitem")
public class ConstructCertItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;// id

	private Long sn;// 序号

	private String content;// 内容

	private BigDecimal price;// 单价

	private BigDecimal numb;// 数量

	private String unit;// 单位

	private BigDecimal amount;// 结算价

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getNumb() {
		return numb;
	}

	public void setNumb(BigDecimal numb) {
		this.numb = numb;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
