package cn.fyg.pm.domain.model.project;

import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.generator.Pattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPattern;
import cn.fyg.pm.domain.model.nogenerator.generator.impl.AbstractPatternFactory;
import cn.fyg.pm.domain.model.nogenerator.norecord.NoKey;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;

@Component("projectNo")
public class ProjectNo extends AbstractPatternFactory<Project> {
	
	private class PatternImp extends AbstractPattern<Project>{

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
			return Long.valueOf(99);
		}

		@Override
		public void initNoKey(NoKey noKey, Project t) {
			noKey.setSys("D");
			noKey.setFlag("XM");
			int year=DateUtil.year();
			String pref=String.valueOf(year).substring(2);
			noKey.setPref(pref);
		}
		
	}


	@Override
	public Pattern<Project> doCreate(Project t) {
		return new PatternImp().init(t);
	}
	

}
