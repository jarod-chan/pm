package cn.fyg.pm.application.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.IdentifyService;
import cn.fyg.pm.domain.model.identify.rolepm.Rolepm;
import cn.fyg.pm.domain.model.identify.rolepm.RolepmRepository;
import cn.fyg.pm.domain.model.identify.userrole.UserFnrole;
import cn.fyg.pm.domain.model.identify.userrole.UserFnroleRepository;
import cn.fyg.pm.domain.model.user.User;

@Service("identifyService")
public class IdentifyServiceImpl implements IdentifyService {
	
	@Autowired
	UserFnroleRepository userFnroleRepository;
	@Autowired
	RolepmRepository rolepmRepository;

	@Override
	public List<String> findUserRole(User user) {
		List<String> roleKeys=new ArrayList<String>();
		List<UserFnrole> userFnroles = this.userFnroleRepository.findByUser(user);
		for (UserFnrole userFnrole : userFnroles) {
			roleKeys.add(userFnrole.getFnrole().getKey());
		}
		return roleKeys;
	}

	@Override
	public List<String> findUserPermission(User user) {
		
		List<Rolepm> allRolepms=new ArrayList<Rolepm>();
		List<UserFnrole> userFnroles = this.userFnroleRepository.findByUser(user);
		for (UserFnrole userFnrole : userFnroles) {
			List<Rolepm> rolepms=this.rolepmRepository.findByFnrole(userFnrole.getFnrole());
			allRolepms.addAll(rolepms);
		}
		List<String> permissionKeysList=new ArrayList<String>();
		for (Rolepm rolepm : allRolepms) {
			String permissionKey = rolepm.getPermission().getKey();
			if(!permissionKeysList.contains(permissionKey)){
				permissionKeysList.add(permissionKey);
			}
		}
		return permissionKeysList;
	}

}
