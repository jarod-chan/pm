package cn.fyg.pm.domain.model.design.designnoti;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.shared.EntityItem;

/**
 *设计变更联系单明细
 */
@Entity
@Table(name="pm_designnotiitem")
public class DesignNotiItem implements EntityItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id

	private Long sn;//序号
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)  
	@JoinColumn(name="designnoti_id")
	private DesignNoti designNoti;
	
	private String content;//内容
	
	private String graphno;//图号

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

	public DesignNoti getDesignNoti() {
		return designNoti;
	}

	public void setDesignNoti(DesignNoti designNoti) {
		this.designNoti = designNoti;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGraphno() {
		return graphno;
	}

	public void setGraphno(String graphno) {
		this.graphno = graphno;
	}
	
	
	
}
