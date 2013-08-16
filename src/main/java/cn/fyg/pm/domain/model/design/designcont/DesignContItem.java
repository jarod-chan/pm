package cn.fyg.pm.domain.model.design.designcont;

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
@Table(name="pm_designcontitem")
public class DesignContItem  implements EntityItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id

	private Long sn;//序号
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="designcont_id")
	private DesignCont designCont;
	
	private String content;//内容

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

	public DesignCont getDesignCont() {
		return designCont;
	}

	public void setDesignCont(DesignCont designCont) {
		this.designCont = designCont;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
