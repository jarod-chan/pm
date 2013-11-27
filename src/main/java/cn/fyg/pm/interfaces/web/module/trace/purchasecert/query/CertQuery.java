package cn.fyg.pm.interfaces.web.module.trace.purchasecert.query;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCert;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertSpecs;
import cn.fyg.pm.domain.model.purchase.purchasecert.PurchaseCertState;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.CommonQuery;

public class CertQuery extends CommonQuery<PurchaseCert> {

	@Override
	public void doSpec(List<Specification<PurchaseCert>> specs) {
		if (this.getProject() != null) {
			specs.add(PurchaseCertSpecs.inProject(this.getProject()));
		}
		if (StringUtils.isNotBlank(this.getNo())) {
			specs.add(PurchaseCertSpecs.noLike(this.getNo().trim()));
		}
		if (this.getCreatedate_beg() != null) {
			specs.add(PurchaseCertSpecs.createAfterDate(this
					.getCreatedate_beg()));
		}
		if (this.getCreatedate_end() != null) {
			Date nextday = DateUtil.nextDay(this.getCreatedate_end());
			specs.add(PurchaseCertSpecs.createBeforeDate(nextday));
		}
		if (this.getState() != null) {
			mapState(specs, this.getState());
		}
	}

	private void mapState(List<Specification<PurchaseCert>> specs,
			String stateValue) {
		if (stateValue.equals("ext-all")) {
			return;
		}
		if (stateValue.equals("ext-notf")) {
			specs.add(PurchaseCertSpecs.notState(PurchaseCertState.finish));
			return;
		}
		specs.add(PurchaseCertSpecs.isState(PurchaseCertState.valueOf(stateValue)));
	}
}
