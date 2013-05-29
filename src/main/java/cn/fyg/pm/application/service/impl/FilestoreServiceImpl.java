package cn.fyg.pm.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.service.FilestoreService;
import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.model.fileupload.filestore.FilestoreRepository;

@Service("filestoreService")
public class FilestoreServiceImpl implements FilestoreService {
	
	@Autowired
	FilestoreRepository filestoreRepository;
	
	
	@Override
	@Transactional
	public Filestore save(Filestore filestore) {
		return filestoreRepository.save(filestore);
	}


	@Override
	public Filestore create(String fullname) {
		int len=fullname.lastIndexOf('.');
		String filename=fullname.substring(0, len);
		String suffix=fullname.substring(len+1);
		Filestore filestore=new Filestore();
		filestore.setFilename(filename);
		filestore.setSuffix(suffix);
		return filestore;
	}

}
