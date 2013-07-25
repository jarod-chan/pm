package cn.fyg.pm.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.OpinionItem;
import cn.fyg.pm.domain.model.workflow.opinion.OpinionRepository;
import cn.fyg.pm.domain.shared.BusiCode;

@Service("opinionService")
public class OpinionServiceImpl implements OpinionService {
	
	@Autowired
	OpinionRepository opinionRepository;

	@Override
	@Transactional
	public Opinion append(Opinion opinion) {
		opinion.setDate(new Date());
		for (OpinionItem opinionItem : opinion.getOpinionItems()) {
			opinionItem.setBusinessId(opinion.getBusinessId());
			opinionItem.setBusiCode(opinion.getBusiCode());
		}
		return opinionRepository.save(opinion);
	}

	@Override
	public List<Opinion> listOpinions(BusiCode busiCode, Long businessId) {
		return opinionRepository.findByBusiCodeAndBusinessIdOrderByIdAsc(busiCode, businessId);
	}

	@Override
	@Transactional
	public void clear(BusiCode busiCode, Long businessId) {
		List<Opinion> opinions = opinionRepository.findByBusiCodeAndBusinessIdOrderByIdAsc(busiCode, businessId);
		this.opinionRepository.delete(opinions);
	}

}
