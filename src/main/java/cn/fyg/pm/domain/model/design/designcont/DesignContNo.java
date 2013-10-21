package cn.fyg.pm.domain.model.design.designcont;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;

@Component("designContNo")
public class DesignContNo extends AbstractPatternFactory<DesignCont> {
	
	private class PatternImp extends AbstractPattern<DesignCont>{

		@Override
		public void setNo(String no) {
			this.t.setNo(no);
		}
		
		@Override
		public String getNo() {
			return this.t.getNo();
		}

		@Override
		public Long initLimmit() {
			return Long.valueOf(9999);
		}

		@Override
		public void initNoKey(NoKey noKey, DesignCont t) {
			noKey.setSys("F");
			noKey.setFlag("");
			int year=DateUtil.year();
			String pref=String.valueOf(year).substring(2);
			noKey.setPref(pref);
		}
	}

	@Override
	public Pattern<DesignCont> doCreate(DesignCont t) {
		return new PatternImp().init(t);
	}


}
