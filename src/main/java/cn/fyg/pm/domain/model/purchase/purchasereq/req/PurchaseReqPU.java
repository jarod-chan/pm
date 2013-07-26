package cn.fyg.pm.domain.model.purchase.purchasereq.req;

import cn.fyg.pm.domain.model.nogenerator.NoKey;
import cn.fyg.pm.domain.model.nogenerator.NoPattern;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;

public class PurchaseReqPU implements NoPatternUnit {
	
	private PurchaseReq purchaseReq;

	public PurchaseReqPU(PurchaseReq purchaseReq) {
		super();
		this.purchaseReq = purchaseReq;
	}

	@Override
	public NoPattern getNoPattern() {
		NoKey nokey=new NoKey();
		nokey.setSys("F");
		nokey.setFlag("CR");
		String projectNo=this.purchaseReq.getPurchaseKey().getProject().getNo();
		String[] noParts=projectNo.split("-");
		nokey.setPref(noParts[0].substring(3)+noParts[1]);
		Long limit=Long.valueOf(999);
	    NoPattern noPattern = new NoPattern(nokey,limit);
	    return noPattern;
	}

	@Override
	public void setNo(String no) {
		this.purchaseReq.setBusino(no);

	}

	@Override
	public String getNo() {
		return this.purchaseReq.getBusino();
	}

}
