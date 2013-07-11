package cn.fyg.pm.domain.model.construct.constructcont;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;

/**
 *用于审批完成时流水号生成
 */
public class ConstructContPU implements NoPatternUnit {
	
	private ConstructCont constructCont;
	
	public ConstructContPU(ConstructCont constructCont){
		this.constructCont=constructCont;
	}

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("F");
		nokey.setFlag("LS");
		String contractNo=constructCont.getConstructKey().getContract().getNo();
		String[] noParts=contractNo.split("-");
		nokey.setPref(noParts[2]+noParts[3]);
		Long limit=Long.valueOf(999);
	    NoPattern noPattern = new NoPattern(nokey,limit);
	    return noPattern;
	}

	@Override
	public void setNo(String no) {
		this.constructCont.setBusino(no);
	}

	@Override
	public String getNo() {
		return this.constructCont.getBusino();
	}

}
