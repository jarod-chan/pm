package cn.fyg.pm.domain.filestore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *文件存储
 */
@Entity
@Table(name="pm_filestore")
public class Filestore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//文件id
	
	private String filename;//文件名
	
	private String suffiix;//后缀

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSuffiix() {
		return suffiix;
	}

	public void setSuffiix(String suffiix) {
		this.suffiix = suffiix;
	}
	
	

}
