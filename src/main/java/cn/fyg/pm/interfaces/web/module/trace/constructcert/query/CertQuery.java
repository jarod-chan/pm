package cn.fyg.pm.interfaces.web.module.trace.constructcert.query;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertSpecs;
import cn.fyg.pm.domain.model.construct.constructcert.ConstructCertState;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.ConstructcertQueryRef;

public class CertQuery extends ConstructcertQueryRef<ConstructCert> {

	@Override
	public void doSpec(List<Specification<ConstructCert>> specs) {
		if (this.getProject() != null) {
			specs.add(ConstructCertSpecs.inProject(this.getProject()));
		}
		if (StringUtils.isNotBlank(this.getNo())) {
			specs.add(ConstructCertSpecs.noLike(this.getNo().trim()));
		}
		if (this.getSupplier() != null) {
			if (this.getSupplier().getId() != null) {
				specs.add(ConstructCertSpecs.withSupplier(this.getSupplier()));
			}
		}
		if (this.getCreatedate_beg() != null) {
			specs.add(ConstructCertSpecs.createAfterDate(this
					.getCreatedate_beg()));
		}
		if (this.getCreatedate_end() != null) {
			Date nextday = DateUtil.nextDay(this.getCreatedate_end());
			specs.add(ConstructCertSpecs.createBeforeDate(nextday));
		}
		if (this.getSpecialty() != null) {
			specs.add(ConstructCertSpecs.isSpecialty(this.getSpecialty()));
		}
		if (this.getState() != null) {
			mapState(specs, this.getState());
		}
	}

	private void mapState(List<Specification<ConstructCert>> specs,
			String stateValue) {
		if (stateValue.equals("ext-all")) {
			return;
		}
		if (stateValue.equals("ext-notf")) {
			specs.add(ConstructCertSpecs.notState(ConstructCertState.finish));
			return;
		}
		specs.add(ConstructCertSpecs.isState(ConstructCertState
				.valueOf(stateValue)));
	}

}
