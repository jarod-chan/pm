package cn.fyg.pm.interfaces.web.module.trace.designcont.query;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.design.designcont.DesignCont;
import cn.fyg.pm.domain.model.design.designcont.DesignContSpecs;
import cn.fyg.pm.domain.model.design.designcont.DesignContState;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.CommonQueryRef;

public class ContQuery extends CommonQueryRef<DesignCont> {

	@Override
	public void doSpec(List<Specification<DesignCont>> specs) {
		if (this.getProject() != null) {
			specs.add(DesignContSpecs.inProject(this.getProject()));
		}
		if (StringUtils.isNotBlank(this.getNo())) {
			specs.add(DesignContSpecs.noLike(this.getNo().trim()));
		}
		if (this.getCreatedate_beg() != null) {
			specs.add(DesignContSpecs.createAfterDate(this.getCreatedate_beg()));
		}
		if (this.getCreatedate_end() != null) {
			Date nextday = DateUtil.nextDay(this.getCreatedate_end());
			specs.add(DesignContSpecs.createBeforeDate(nextday));
		}
		if (this.getState() != null) {
			mapState(specs, this.getState());
		}
	}

	private void mapState(List<Specification<DesignCont>> specs,
			String stateValue) {
		if (stateValue.equals("ext-all")) {
			return;
		}
		if (stateValue.equals("ext-notf")) {
			specs.add(DesignContSpecs.notState(DesignContState.finish));
			return;
		}
		specs.add(DesignContSpecs.isState(DesignContState.valueOf(stateValue)));
	}

}
