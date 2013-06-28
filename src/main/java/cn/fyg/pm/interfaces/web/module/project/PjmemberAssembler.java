package cn.fyg.pm.interfaces.web.module.project;

import java.util.ArrayList;
import java.util.List;

import cn.fyg.pm.domain.model.user.User;

public class PjmemberAssembler {
	
	public static List<PjmemberDto> build(List<User> userList, List<User> projectUsers) {
		List<PjmemberDto> plt=new ArrayList<PjmemberDto>();
		for (User user : userList) {
			PjmemberDto pjmemberDto = new PjmemberDto();
			pjmemberDto.setUser(user);
			pjmemberDto.setChecked(projectUsers.contains(user));
			plt.add(pjmemberDto);
		}
		return plt;
	}

}
