package cn.fyg.pm.domain.model.constructcert;

import java.util.Date;
import java.util.UUID;

import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public class ConstructCertFactory {
	
	public static ConstructCert create(User user,Project project,ConstructCertState state){
		ConstructKey constructKey = new ConstructKey();
		constructKey.setProject(project);
		ConstructCert constructCert = new ConstructCert();
		constructCert.setNo(UUID.randomUUID().toString().toUpperCase().substring(0, 4));
		constructCert.setState(state);
		constructCert.setLeader(project.getLeader());
		constructCert.setCreater(user);
		constructCert.setCreatedate(new Date());
		constructCert.setConstructKey(constructKey);
		return constructCert;
	}

}
