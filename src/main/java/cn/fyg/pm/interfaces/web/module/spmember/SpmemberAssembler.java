package cn.fyg.pm.interfaces.web.module.spmember;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;

public class SpmemberAssembler {
	
	public static List<SpmemberDto> build(List<User> users,Map<User, Supplier> userSupplier) {
		ArrayList<SpmemberDto> spmemberDtos = new ArrayList<SpmemberDto>();
		for(User user:users){
			SpmemberDto spmemberDto = new SpmemberDto();
			spmemberDto.setUser(user);
			spmemberDto.setSupplier(userSupplier.get(user));
			spmemberDtos.add(spmemberDto);
		}
		return spmemberDtos;
	}

}
