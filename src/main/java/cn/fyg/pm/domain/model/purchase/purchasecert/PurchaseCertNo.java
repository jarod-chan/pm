package cn.fyg.pm.domain.model.purchase.purchasecert;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;

@Component("purchaseCertNo")
public class PurchaseCertNo extends AbstractPatternFactory<PurchaseCert> {

	private class PatternImp extends AbstractPattern<PurchaseCert>{

		@Override
		public String getNo() {
			return this.t.getNo();
		}

		@Override
		public void setNo(String no) {
			this.t.setNo(no);
		}

		@Override
		public Long initLimmit() {
			return Long.valueOf(9999);
		}

		@Override
		public void initNoKey(NoKey noKey, PurchaseCert purchaseCert) {
			noKey.setSys("D");
			noKey.setFlag("");
			int year=DateUtil.year();
			String pref=String.valueOf(year).substring(2);
			noKey.setPref(pref);
		}
		
	}


	@Override
	public Pattern<PurchaseCert> doCreate(PurchaseCert t) {
		return new PatternImp().init(t);
	}
}
