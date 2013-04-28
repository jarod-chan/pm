package cn.fyg.pm.domain.model.fileupload.filestore;

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
	
	private String suffix;//后缀

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

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	
	

}
