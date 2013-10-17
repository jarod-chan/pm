package cn.fyg.pm.domain.model.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator2.generator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator2.generator.impl.AbstractPatternGene;
import cn.fyg.pm.domain.model.nogenerator2.look.LockService;
import cn.fyg.pm.infrastructure.tool.date.DateUtil;

@Component("project_no")
public class ProjectNo extends AbstractPatternGene<Project> {
	
	@Override
	public boolean lockCondition() {
		return this.object.getId()==null;
	}

	
	@Autowired
	@Override
	public void setLockService(LockService lockService) {
		this.lockService=lockService;
	}
	

	@Override
	public NoPattern initNoPatternWithObject(Project project) {
		NoKey nokey=new NoKey();
		nokey.setSys("D");
		nokey.setFlag("XM");
		int year=DateUtil.year();
		String pref=String.valueOf(year).substring(2);
		nokey.setPref(pref);
		Long limit=Long.valueOf(99);
		NoPattern noPattern = new NoPattern(nokey,limit);
	    return noPattern;
	}

	@Override
	public void setNo(String no) {
		this.object.setNo(no);
	}

	@Override
	public String getNo() {
		return this.object.getNo();
	}	

	
}
