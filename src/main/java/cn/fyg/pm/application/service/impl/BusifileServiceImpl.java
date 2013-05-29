package cn.fyg.pm.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.service.BusifileService;
import cn.fyg.pm.domain.model.fileupload.busifile.Busifile;
import cn.fyg.pm.domain.model.fileupload.busifile.BusifileRepository;
import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.shared.BusiCode;

@Service("busifileService")
public class BusifileServiceImpl implements BusifileService {
	
	@Autowired
	BusifileRepository busifileRepository;

	@Override
	@Transactional
	public List<Busifile> save(List<Busifile> busifileList) {
		return (List<Busifile>) busifileRepository.save(busifileList);
	}

	@Override
	public List<Filestore> findFilestores(BusiCode busiCode, Long busiId) {
		List<Busifile> busifileList = busifileRepository.findByBusiCodeAndBusiIdOrderByIdAsc(busiCode, busiId);
		ArrayList<Filestore> filestoreList = new ArrayList<Filestore>();
		for (Busifile busifile : busifileList) {
			busifileList.add(busifile);
		}
		return filestoreList;
	}

	@Override
	@Transactional
	public void deleteByBusiCodeAndBusiId(BusiCode busiCode, Long busiId) {
		List<Busifile> busifileList = busifileRepository.findByBusiCodeAndBusiIdOrderByIdAsc(busiCode, busiId);
		this.busifileRepository.delete(busifileList);
	}

}
