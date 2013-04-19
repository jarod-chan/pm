package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.FilestoreService;
import cn.fyg.pm.domain.filestore.Filestore;
import cn.fyg.pm.domain.filestore.FilestoreRepository;

@Service
public class FilestoreServiceImpl implements FilestoreService {
	
	@Autowired
	FilestoreRepository filestoreRepository;

	@Override
	@Transactional
	public Filestore save(Filestore filestore) {
		return filestoreRepository.save(filestore);
	}

}
