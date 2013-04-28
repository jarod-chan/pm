package cn.fyg.pm.domain.model.fileupload.busifile;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.shared.BusiCode;

@Entity
@Table(name="pm_busifile")
public class Busifile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//文件id
	
	@Enumerated(EnumType.STRING)
	private BusiCode busiCode;//业务对象类型
	
	private Long busiId;//业务对象id
	
	@ManyToOne(targetEntity=Filestore.class)
	@JoinColumn(name="filestore_id")
	private Filestore filestore;//文件存储

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

	public Long getBusiId() {
		return busiId;
	}

	public void setBusiId(Long busiId) {
		this.busiId = busiId;
	}

	public Filestore getFilestore() {
		return filestore;
	}

	public void setFilestore(Filestore filestore) {
		this.filestore = filestore;
	}

	
}
