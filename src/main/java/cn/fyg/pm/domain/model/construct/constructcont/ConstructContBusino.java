package cn.fyg.pm.domain.model.construct.constructcont;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("constructContBusino")
public class ConstructContBusino extends AbstractPatternFactory<ConstructCont> {
	
	private class PatternImp extends AbstractPattern<ConstructCont>{

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
		public void initNoKey(NoKey noKey, ConstructCont constructCont) {
			noKey.setSys("F");
			noKey.setFlag("LS");
			String contractNo=constructCont.getConstructKey().getContract().getNo();
			String noParts=contractNo.substring(3);
			noKey.setPref(noParts);
		}

	}

	@Override
	public Pattern<ConstructCont> doCreate(ConstructCont t) {
		return new PatternImp().init(t);
	}


}
