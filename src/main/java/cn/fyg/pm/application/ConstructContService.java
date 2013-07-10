package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;


public interface ConstructContService extends ServiceQuery<ConstructCont> {
	
	List<ConstructCont> findAll();
	
	ConstructCont save(ConstructCont constructCont);
	
	void delete(Long id);
	
	ConstructCont find(Long id);

	ConstructCont findByConstructKey(ConstructKey constructKey);

	ConstructCont create(User creater,Project project, ConstructContState state);

	List<ConstructCont> findByProject(Project project);
	
	List<ConstructCont> findByProjectAndSupplier(Project project,Supplier supplier);
}
