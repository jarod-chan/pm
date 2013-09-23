package cn.fyg.pm.domain.model.design.designcont;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;

public class DesignContPU implements NoPatternUnit {
	
	private DesignCont designCont;

	public DesignContPU(DesignCont designCont) {
		super();
		this.designCont = designCont;
	}

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("F");
		nokey.setFlag("DC");
		String projectNo=this.designCont.getProject().getNo();
		String noParts=projectNo.substring(3);
		String typeCode = this.designCont.getTechType().getCode();
		nokey.setPref(noParts+typeCode);
		Long limit=Long.valueOf(999);
	    NoPattern noPattern = new NoPattern(nokey,limit);
	    noPattern.setSeparator("");
	    return noPattern;
	}

	@Override
	public void setNo(String no) {
		this.designCont.setBusino(no);
	}

	@Override
	public String getNo() {
		return this.designCont.getBusino();
	}

}
