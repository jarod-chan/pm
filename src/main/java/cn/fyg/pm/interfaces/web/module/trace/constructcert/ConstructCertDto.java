package cn.fyg.pm.interfaces.web.module.trace.constructcert;

import cn.fyg.pm.domain.model.construct.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;

public class ConstructCertDto {

	private ConstructCert constructCert;
	
	private ConstructCont constructCont;

	public ConstructCert getConstructCert() {
		return constructCert;
	}

	public void setConstructCert(ConstructCert constructCert) {
		this.constructCert = constructCert;
	}

	public ConstructCont getConstructCont() {
		return constructCont;
	}

	public void setConstructCont(ConstructCont constructCont) {
		this.constructCont = constructCont;
	}
	
	
}
