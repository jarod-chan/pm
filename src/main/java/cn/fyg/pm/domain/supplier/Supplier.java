package cn.fyg.pm.domain.supplier;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *供应商：
 *合同签订对象
 */
@Entity
@Table(name="pm_supplier")
public class Supplier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	//供应商id
	private String no;		//编号
	private String name;	//名称
	@Enumerated(EnumType.STRING)
	private Supptype type;	// 材料供应商 、设计院、监理公司、承包商
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Supptype getType() {
		return type;
	}
	public void setType(Supptype type) {
		this.type = type;
	}
	
	
}
