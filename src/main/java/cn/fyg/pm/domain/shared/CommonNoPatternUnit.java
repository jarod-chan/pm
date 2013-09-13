package cn.fyg.pm.domain.shared;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;

public abstract class CommonNoPatternUnit implements NoPatternUnit {

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("T");
		nokey.setFlag("");
		int year=DateUtil.year();
		String pref=String.valueOf(year).substring(2);
		nokey.setPref(pref);
		Long limit=Long.valueOf(9999);
	    NoPattern noPattern = new NoPattern(nokey,limit);
	    noPattern.setSeparator("");
	    return noPattern;
	}

}
