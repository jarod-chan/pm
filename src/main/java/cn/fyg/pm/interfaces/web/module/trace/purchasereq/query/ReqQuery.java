package cn.fyg.pm.interfaces.web.module.trace.purchasereq.query;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqSpecs;
import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReqState;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;
import cn.fyg.pm.interfaces.web.shared.query.CommonQueryRef;

public class ReqQuery extends CommonQueryRef<PurchaseReq> {

	@Override
	public void doSpec(List<Specification<PurchaseReq>> specs) {
		if (this.getProject() != null) {
			specs.add(PurchaseReqSpecs.inProject(this.getProject()));
		}
		if (StringUtils.isNotBlank(this.getNo())) {
			specs.add(PurchaseReqSpecs.noLike(this.getNo().trim()));
		}
		if (this.getCreatedate_beg() != null) {
			specs.add(PurchaseReqSpecs.createAfterDate(this.getCreatedate_beg()));
		}
		if (this.getCreatedate_end() != null) {
			Date nextday = DateUtil.nextDay(this.getCreatedate_end());
			specs.add(PurchaseReqSpecs.createBeforeDate(nextday));
		}
		if (this.getState() != null) {
			mapState(specs, this.getState());
		}
	}

	private void mapState(List<Specification<PurchaseReq>> specs,
			String stateValue) {
		if (stateValue.equals("ext-all")) {
			return;
		}
		if (stateValue.equals("ext-notf")) {
			specs.add(PurchaseReqSpecs.notState(PurchaseReqState.finish));
			return;
		}
		specs.add(PurchaseReqSpecs.isState(PurchaseReqState.valueOf(stateValue)));
	}

}
