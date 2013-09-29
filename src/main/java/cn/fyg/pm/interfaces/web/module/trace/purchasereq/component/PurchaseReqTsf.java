package cn.fyg.pm.interfaces.web.module.trace.purchasereq.component;

import cn.fyg.pm.domain.model.purchase.purchasereq.req.PurchaseReq;
import cn.fyg.pm.infrastructure.tool.fmt.Fmt;
import cn.fyg.pm.interfaces.web.shared.component.Transfer;

public class PurchaseReqTsf  implements Transfer<PurchaseReq,PurchaseReqSmp>{

	@Override
	public PurchaseReqSmp transfer(PurchaseReq from) {
		PurchaseReqSmp to = new PurchaseReqSmp();
		to.setPurchaseKeyId(from.getPurchaseKey().getId());
		to.setId(from.getId());
		to.setNo(from.getNo());
		to.setPlandate(Fmt.toStr(from.getPlandate(), "yyyy-MM-dd"));
		to.setState(from.getState().getName());
		to.setCreater_name(from.getCreater()!=null?Fmt.toStr(from.getCreater().getName()):"");
		to.setCreatedate(Fmt.toStr(from.getCreatedate(),"yyyy-MM-dd"));
		return to;
	}

}
