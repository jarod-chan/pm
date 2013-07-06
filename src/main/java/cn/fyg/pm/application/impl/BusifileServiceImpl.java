package cn.fyg.pm.application.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.BusifileService;
import cn.fyg.pm.domain.model.fileupload.busifile.Busifile;
import cn.fyg.pm.domain.model.fileupload.busifile.BusifileRepository;
import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.shared.BusiCode;

@Service("busifileService")
public class BusifileServiceImpl implements BusifileService {
	
	@Autowired
	BusifileRepository busifileRepository;


	@Override
	public List<Filestore> findFilestores(BusiCode busiCode, Long busiId) {
		List<Busifile> busifileList = busifileRepository.findByBusiCodeAndBusiIdOrderByIdAsc(busiCode, busiId);
		ArrayList<Filestore> filestoreList = new ArrayList<Filestore>();
		for (Busifile busifile : busifileList) {
			filestoreList.add(busifile.getFilestore());
		}
		return filestoreList;
	}

	@Override
	@Transactional
	public void removeAssociatedFile(BusiCode busiCode, Long busiId) {
		List<Busifile> busifileList = busifileRepository.findByBusiCodeAndBusiIdOrderByIdAsc(busiCode, busiId);
		this.busifileRepository.delete(busifileList);
	}

	@Override
	@Transactional
	public void associateFile(BusiCode busiCode, Long busiId,Long[] filestoreIds) {
		List<Busifile> busifileList = busifileRepository.findByBusiCodeAndBusiIdOrderByIdAsc(busiCode, busiId);
		List<Long> filestoreIdList = filestoreIds==null?new ArrayList<Long>():new ArrayList<Long>(Arrays.asList(filestoreIds));
		for(Busifile busifile:busifileList){
			Long filestoreId = busifile.getFilestore().getId();
			if(filestoreIdList.contains(filestoreId)){
				filestoreIdList.remove(filestoreId);
			}else{
				this.busifileRepository.delete(busifile);
			}
		}
		for (Long filestoreId : filestoreIdList) {
			Busifile busifile=new Busifile();
			busifile.setBusiCode(busiCode);
			busifile.setBusiId(busiId);
			Filestore filestore=new Filestore();
			filestore.setId(filestoreId);
			busifile.setFilestore(filestore);
			this.busifileRepository.save(busifile);
		}
	}

}
