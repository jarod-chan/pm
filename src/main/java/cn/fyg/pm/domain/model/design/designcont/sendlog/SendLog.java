package cn.fyg.pm.domain.model.design.designcont.sendlog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *记录文件发放数量
 */
@Entity
@Table(name="pm_designcontsend")
public class SendLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//id
	
	private Long designcont_id;//技术变更联系单id
	
	private String receiver;	//接收人
	
	@Column(name="date_")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;//日期
	
	private Long numb;//文件数量

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDesigncont_id() {
		return designcont_id;
	}

	public void setDesigncont_id(Long designcont_id) {
		this.designcont_id = designcont_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getNumb() {
		return numb;
	}

	public void setNumb(Long numb) {
		this.numb = numb;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
}
