package cn.fyg.pm.domain.model.construct.constructcert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class ConstructCertFactory {
	
	public static ConstructCert create(User user,Project project,ConstructCertState state,boolean generateNo){
		ConstructKey constructKey = new ConstructKey();
		constructKey.setProject(project);
		ConstructCert constructCert = new ConstructCert();
		if(generateNo){
			constructCert.setNo(UUID.randomUUID().toString().toUpperCase().substring(0, 4));
		}
		constructCert.setState(state);
		constructCert.setLeader(project.getLeader());
		constructCert.setCreater(user);
		constructCert.setCreatedate(new Date());
		constructCert.setConstructKey(constructKey);
		constructCert.setTolsum(new BigDecimal("0.00"));
		return constructCert;
	}

}
