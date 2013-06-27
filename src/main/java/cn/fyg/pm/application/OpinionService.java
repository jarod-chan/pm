package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.shared.BusiCode;


public interface OpinionService {
	
	Opinion append(Opinion opinion);
	
	List<Opinion> listOpinions(BusiCode busiCode,Long businessId);
	
	void clear(BusiCode busiCode,Long businessId);
}
