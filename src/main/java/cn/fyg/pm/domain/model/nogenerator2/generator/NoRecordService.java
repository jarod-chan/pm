package cn.fyg.pm.domain.model.nogenerator2.generator;

import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;


public interface NoRecordService {

	String generateNextNo(NoPattern pattern);

	void rollbackLastNo(NoPattern pattern, String lastNo)
			throws NoNotLastException;
	

}
