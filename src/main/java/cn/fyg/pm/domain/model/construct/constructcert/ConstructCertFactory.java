package cn.fyg.pm.domain.model.construct.constructcert;

import java.math.BigDecimal;
import java.util.Date;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class ConstructCertFactory {
	
	public static ConstructCert create(User user,User projectLeader,Project project,ConstructCertState state){
		ConstructCert constructCert = new ConstructCert();
		constructCert.setState(state);
		constructCert.setLeader(projectLeader);
		constructCert.setCreater(user);
		constructCert.setCreatedate(new Date());
		constructCert.setTolsum(new BigDecimal("0.00"));
		return constructCert;
	}

}
