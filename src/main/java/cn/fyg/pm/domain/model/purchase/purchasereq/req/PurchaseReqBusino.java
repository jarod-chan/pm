package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("purchaseReqBusino")
public class PurchaseReqBusino extends AbstractPatternFactory<PurchaseReq> {
	
	private class PatternImp extends AbstractPattern<PurchaseReq>{

		@Override
		public String getNo() {
			return this.t.getBusino();
		}

		@Override
		public void setNo(String no) {
			this.t.setBusino(no);
		}

		@Override
		public Long initLimmit() {
			return Long.valueOf(999);
		}

		@Override
		public void initNoKey(NoKey noKey, PurchaseReq purchaseReq) {
			noKey.setSys("F");
			noKey.setFlag("CR");
			String projectNo=purchaseReq.getPurchaseKey().getProject().getNo();
			String noParts=projectNo.substring(3);
			noKey.setPref(noParts);
		}
		
	}


	@Override
	public Pattern<PurchaseReq> doCreate(PurchaseReq t) {
		return new PatternImp().init(t);
	}

}
