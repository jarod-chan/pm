package cn.fyg.pm.domain.model.purchase.purchasecert;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;

public class PurchaseCertPU implements NoPatternUnit {
	
	private PurchaseCert purchaseCert;

	public PurchaseCertPU(PurchaseCert purchaseCert) {
		this.purchaseCert=purchaseCert;
	}

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("F");
		nokey.setFlag("CA");
		String projectNo=this.purchaseCert.getPurchaseKey().getProject().getNo();
		String[] noParts=projectNo.split("-");
		nokey.setPref(noParts[0].substring(3)+noParts[1]);
		Long limit=Long.valueOf(999);
	    NoPattern noPattern = new NoPattern(nokey,limit);
	    return noPattern;
	}

	@Override
	public void setNo(String no) {
		this.purchaseCert.setBusino(no);
	}

	@Override
	public String getNo() {
		return this.purchaseCert.getBusino();
	}

}