package cn.fyg.pm.interfaces.web.module.spmember;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fyg.pm.domain.model.spmember.Spmember;
import cn.fyg.pm.domain.model.user.User;

public class SpmemberAssembler {
	
	public static List<SpmemberDto> build(List<User> users,List<Spmember> spmembers){
		Map<String,Spmember> userKeyToSpmemberMap=getUserKeyToSpmemberMap(spmembers);
		
		ArrayList<SpmemberDto> spmemberDtos = new ArrayList<SpmemberDto>();
		for(User user:users){
			SpmemberDto spmemberDto = new SpmemberDto();
			spmemberDto.setUser(user);
			spmemberDto.setSpmember(userKeyToSpmemberMap.get(user.getKey()));
			spmemberDtos.add(spmemberDto);
		}
		return spmemberDtos;
	}

	private static Map<String, Spmember> getUserKeyToSpmemberMap(List<Spmember> spmembers) {
		Map<String,Spmember> userKeyToSpmember = new HashMap<String,Spmember>();
		for (Spmember spmember : spmembers) {
			userKeyToSpmember.put(spmember.getUser().getKey(), spmember);
		}
		return userKeyToSpmember;
	}

}
