package cn.fyg.pm.domain.model.nogenerator2;


public interface NoRecordService {
	
	NoRecord2  getNoRecord(NoPattern pattern);
	
	NoRecord2  save(NoRecord2 noRecord);

}
