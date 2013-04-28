package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.fileupload.busifile.Busifile;
import cn.fyg.pm.domain.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.shared.BusiCode;

public interface BusifileService {
	
	List<Busifile> save(List<Busifile> busifileList);

	List<Filestore> findFilestores(BusiCode busiCode,Long busiId);
	
	void deleteByBusiCodeAndBusiId(BusiCode busiCode,Long busiId);
}
