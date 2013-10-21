package cn.fyg.pm.domain.model.design.designnoti;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;

@Component("designNotiBusino")
public class DesignNotiBusino extends AbstractPatternFactory<DesignNoti> {
	
	private class PatternImp extends AbstractPattern<DesignNoti>{

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
		public void initNoKey(NoKey noKey, DesignNoti designNoti) {
			noKey.setSys("F");
			noKey.setFlag("DN");
			String projectNo=designNoti.getProject().getNo();
			String noParts=projectNo.substring(3);
			String typeCode =designNoti.getTechType().getCode();
			noKey.setPref(noParts+typeCode);
			
		}
	}

	@Override
	public Pattern<DesignNoti> doCreate(DesignNoti t) {
		return new PatternImp().init(t);
	}

}
