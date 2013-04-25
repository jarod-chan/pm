package cn.fyg.pm.domain.constructcert;

import java.util.Date;
import java.util.UUID;

import cn.fyg.pm.domain.user.User;

public class ConstructCertFactory {
	
	public static ConstructCert create(User user){
		ConstructCert constructCert = new ConstructCert();
		constructCert.setNo(UUID.randomUUID().toString().toUpperCase().substring(0, 4));
		constructCert.setState(ConstructCertState.new_);
		constructCert.setCreater(user);
		constructCert.setCreatedate(new Date());
		return constructCert;
	}

}
