package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.domain.model.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;


public interface ConstructContService {
	
	List<ConstructCont> findAll();
	
	ConstructCont save(ConstructCont constructCont);
	
	void delete(Long id);
	
	ConstructCont find(Long id);

	ConstructCont findByConstructKey(ConstructKey constructKey);

	ConstructCont create(User creater);

	List<ConstructCont> findByProject(Project project);
}
