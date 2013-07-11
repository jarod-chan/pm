package cn.fyg.pm.application;

import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;

public interface FilestoreService {
	
	Filestore save(Filestore filestore);
	
	Filestore create(String fullname);
	
	Filestore find(Long id);

}
