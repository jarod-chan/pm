package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.fileupload.filestore.Filestore;
import cn.fyg.pm.domain.shared.BusiCode;

public interface BusifileService {
	
	List<Filestore> findFilestores(BusiCode busiCode,Long busiId);
	
	void removeAssociatedFile(BusiCode busiCode,Long busiId);
	
	void associateFile(BusiCode busiCode,Long busiId, Long[] filestoreIds);
}
