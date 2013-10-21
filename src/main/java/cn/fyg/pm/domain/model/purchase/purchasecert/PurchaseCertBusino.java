package cn.fyg.pm.domain.model.purchase.purchasecert;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("purchaseCertBusino")
public class PurchaseCertBusino extends AbstractPatternFactory<PurchaseCert> {
	
	private class PatternImp extends AbstractPattern<PurchaseCert>{

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
		public void initNoKey(NoKey noKey, PurchaseCert purchaseCert) {
			noKey.setSys("F");
			noKey.setFlag("CA");
			String projectNo=purchaseCert.getPurchaseKey().getProject().getNo();
			String noParts=projectNo.substring(3);
			noKey.setPref(noParts);
		}
		
	}


	@Override
	public Pattern<PurchaseCert> doCreate(PurchaseCert t) {
		return new PatternImp().init(t);
	}

}
