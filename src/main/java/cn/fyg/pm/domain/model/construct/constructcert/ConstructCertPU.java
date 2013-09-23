package cn.fyg.pm.domain.model.construct.constructcert;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;

/**
 *用于审批完成时流水号生成
 */
public class ConstructCertPU implements NoPatternUnit {
	
	private ConstructCert constructCert;
	
	public ConstructCertPU(ConstructCert constructCert){
		this.constructCert=constructCert;
	}

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("F");
		nokey.setFlag("QZ");
		String contractNo=this.constructCert.getConstructKey().getContract().getNo();
		String noParts=contractNo.substring(3);
		nokey.setPref(noParts);
		Long limit=Long.valueOf(999);
	    NoPattern noPattern = new NoPattern(nokey,limit);
	    noPattern.setSeparator("");
	    return noPattern;
	}

	@Override
	public void setNo(String no) {
		this.constructCert.setBusino(no);
	}

	@Override
	public String getNo() {
		return this.constructCert.getBusino();
	}

}
