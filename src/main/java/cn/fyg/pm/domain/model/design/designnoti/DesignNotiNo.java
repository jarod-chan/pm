package cn.fyg.pm.domain.model.design.designnoti;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;

@Component("designNotiNo")
public class DesignNotiNo extends AbstractPatternFactory<DesignNoti> {
	
	private class PatternImp extends AbstractPattern<DesignNoti>{

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
		public void initNoKey(NoKey noKey, DesignNoti t) {
			noKey.setSys("E");
			noKey.setFlag("");
			int year=DateUtil.year();
			String pref=String.valueOf(year).substring(2);
			noKey.setPref(pref);
		}
	}

	@Override
	public Pattern<DesignNoti> doCreate(DesignNoti t) {
		return new PatternImp().init(t);
	}

}
