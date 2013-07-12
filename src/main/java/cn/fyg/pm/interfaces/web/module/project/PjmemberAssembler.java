package cn.fyg.pm.interfaces.web.module.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.fyg.pm.domain.model.pjrole.Pjrole;
import cn.fyg.pm.domain.model.user.User;

public class PjmemberAssembler {
	
	public static List<PjmemberDto> build(List<User> userList,Map<User, Pjrole> projectUserRole) {
		List<PjmemberDto> plt=new ArrayList<PjmemberDto>();
		Set<User> userSet = projectUserRole.keySet();
		for (User user : userList) {
			PjmemberDto pjmemberDto = new PjmemberDto();
			pjmemberDto.setUser(user);
			pjmemberDto.setChecked(userSet.contains(user));
			pjmemberDto.setPjrole(projectUserRole.get(user));
			plt.add(pjmemberDto);
		}
		return plt;
	}

}
