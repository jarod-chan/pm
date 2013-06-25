package cn.fyg.pm.application.service;

import java.util.List;

import cn.fyg.pm.domain.model.spmember.Spmember;
import cn.fyg.pm.domain.model.user.User;

public interface SpmemberService {
	
	List<Spmember> findAll();

	void delete(Long id);

	Spmember save(Spmember spmember);
	
	Spmember findByUser(User user);

}
