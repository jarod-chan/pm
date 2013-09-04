package cn.fyg.pm.domain.model.design.designnoti;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;

public class DesignNotiPU implements NoPatternUnit {
	
	private DesignNoti designNoti;

	public DesignNotiPU(DesignNoti designNoti) {
		super();
		this.designNoti = designNoti;
	}

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("F");
		nokey.setFlag("DN");
		String projectNo=this.designNoti.getProject().getNo();
		String[] noParts=projectNo.split("-");
		String typeCode = this.designNoti.getTechType().getCode();
		nokey.setPref(noParts[0].substring(3)+noParts[1]+typeCode);
		Long limit=Long.valueOf(999);
	    NoPattern noPattern = new NoPattern(nokey,limit);
	    return noPattern;
	}

	@Override
	public void setNo(String no) {
		this.designNoti.setBusino(no);
	}

	@Override
	public String getNo() {
		return this.designNoti.getBusino();
	}

}
