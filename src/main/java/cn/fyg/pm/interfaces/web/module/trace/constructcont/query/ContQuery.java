package cn.fyg.pm.interfaces.web.module.trace.constructcont.query;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContSpecs;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.ConstructcertQueryRef;

public class ContQuery extends ConstructcertQueryRef<ConstructCont> {

	@Override
	public void doSpec(List<Specification<ConstructCont>> specs) {
		if (this.getProject() != null) {
			specs.add(ConstructContSpecs.inProject(this.getProject()));
		}
		if (StringUtils.isNotBlank(this.getNo())) {
			specs.add(ConstructContSpecs.noLike(this.getNo().trim()));
		}
		if (this.getSupplier() != null) {
			if (this.getSupplier().getId() != null) {
				specs.add(ConstructContSpecs.withSupplier(this.getSupplier()));
			}
		}
		if (this.getCreatedate_beg() != null) {
			specs.add(ConstructContSpecs.createAfterDate(this
					.getCreatedate_beg()));
		}
		if (this.getCreatedate_end() != null) {
			Date nextday = DateUtil.nextDay(this.getCreatedate_end());
			specs.add(ConstructContSpecs.createBeforeDate(nextday));
		}
		if (this.getSpecialty() != null) {
			specs.add(ConstructContSpecs.isSpecialty(this.getSpecialty()));
		}
		if (this.getState() != null) {
			mapState(specs, this.getState());

		}
	}

	private void mapState(List<Specification<ConstructCont>> specs,
			String stateValue) {
		if (stateValue.equals("ext-all")) {
			return;
		}
		if (stateValue.equals("ext-notf")) {
			specs.add(ConstructContSpecs.notState(ConstructContState.finish));
			return;
		}
		specs.add(ConstructContSpecs.isState(ConstructContState
				.valueOf(stateValue)));
	}

}
