package cn.fyg.pm.interfaces.web.module.user.compent;

import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.component.Transfer;

public class UserTsf implements Transfer<User,UserSmp>{

	@Override
	public UserSmp transfer(User from) {
		UserSmp to=new UserSmp();
		to.setKey(from.getKey());
		to.setName(from.getName());
		return to;
	}

}
