package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.constructcont.ConstructCont;
import cn.fyg.pm.domain.constructkey.ConstructKey;
import cn.fyg.pm.domain.user.User;


public interface ConstructContService {
	
	List<ConstructCont> findAll();
	
	ConstructCont save(ConstructCont constructCont);
	
	void delete(Long id);
	
	ConstructCont find(Long id);

	ConstructCont findByConstructKey(ConstructKey constructKey);

	ConstructCont create(User creater);
}
