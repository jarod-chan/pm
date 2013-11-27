package cn.fyg.pm.interfaces.web.module.trace.designnoti.query;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiSpecs;
import cn.fyg.pm.domain.model.design.designnoti.DesignNotiState;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.CommonQuery;

public class NotiQuery extends CommonQuery<DesignNoti> {

	@Override
	public void doSpec(List<Specification<DesignNoti>> specs) {
		if (this.getProject() != null) {
			specs.add(DesignNotiSpecs.inProject(this.getProject()));
		}
		if (StringUtils.isNotBlank(this.getNo())) {
			specs.add(DesignNotiSpecs.noLike(this.getNo().trim()));
		}
		if (this.getCreatedate_beg() != null) {
			specs.add(DesignNotiSpecs.createAfterDate(this.getCreatedate_beg()));
		}
		if (this.getCreatedate_end() != null) {
			Date nextday = DateUtil.nextDay(this.getCreatedate_end());
			specs.add(DesignNotiSpecs.createBeforeDate(nextday));
		}
		if (this.getState() != null) {
			mapState(specs, this.getState());
		}
	}

	private void mapState(List<Specification<DesignNoti>> specs,
			String stateValue) {
		if (stateValue.equals("ext-all")) {
			return;
		}
		if (stateValue.equals("ext-notf")) {
			specs.add(DesignNotiSpecs.notState(DesignNotiState.finish));
			return;
		}
		specs.add(DesignNotiSpecs.isState(DesignNotiState.valueOf(stateValue)));
	}

}
