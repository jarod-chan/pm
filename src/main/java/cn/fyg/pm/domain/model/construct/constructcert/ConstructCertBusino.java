package cn.fyg.pm.domain.model.construct.constructcert;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("constructCertBusino")
public class ConstructCertBusino extends AbstractPatternFactory<ConstructCert> {

	private class PatternImp extends AbstractPattern<ConstructCert>{

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
		public void initNoKey(NoKey noKey, ConstructCert constructCert) {
			noKey.setSys("F");
			noKey.setFlag("QZ");
			String contractNo=constructCert.getConstructKey().getContract().getNo();
			String noParts=contractNo.substring(3);
			noKey.setPref(noParts);
		}
	}

	@Override
	public Pattern<ConstructCert> doCreate(ConstructCert t) {
		return new PatternImp().init(t);
	}

}
