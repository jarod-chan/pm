package cn.fyg.pm.interfaces.web.module.consturctcert;

import cn.fyg.pm.domain.constructcert.ConstructCert;
import cn.fyg.pm.domain.constructcont.ConstructCont;

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
