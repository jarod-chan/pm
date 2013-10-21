package cn.fyg.pm.domain.model.design.designcont;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("designContBusino")
public class DesignContBusino extends AbstractPatternFactory<DesignCont> {
	
	private class PatternImp extends AbstractPattern<DesignCont>{

		@Override
		public void setNo(String no) {
			this.t.setBusino(no);
		}
		
		@Override
		public String getNo() {
			return this.t.getBusino();
		}

		@Override
		public Long initLimmit() {
			return Long.valueOf(999);
		}

		@Override
		public void initNoKey(NoKey noKey, DesignCont designCont) {
			noKey.setSys("F");
			noKey.setFlag("DC");
			String projectNo=designCont.getProject().getNo();
			String noParts=projectNo.substring(3);
			String typeCode = designCont.getTechType().getCode();
			noKey.setPref(noParts+typeCode);
		}
	}

	@Override
	public Pattern<DesignCont> doCreate(DesignCont t) {
		return new PatternImp().init(t);
	}

}
