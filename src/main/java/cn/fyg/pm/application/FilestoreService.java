package cn.fyg.pm.application;

import cn.fyg.pm.domain.fileupload.filestore.Filestore;

public interface FilestoreService {
	
	Filestore save(Filestore filestore);
	
	Filestore create(String fullname);

}
