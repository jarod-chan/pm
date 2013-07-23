package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.CommitValidator;
import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;


public interface ConstructContService extends ServiceQuery<ConstructCont>,CommitValidator<ConstructCont> {
	
	List<ConstructCont> findAll();
	
	ConstructCont save(ConstructCont constructCont);
	
	void finish(Long constructContId,String userKey);
	
	void delete(Long id);
	
	ConstructCont find(Long id);

	ConstructCont findByConstructKey(ConstructKey constructKey);

	ConstructCont create(User creater,Project project, ConstructContState state);

	List<ConstructCont> constructContCanBeSelected(Project project,ConstructContState state,Long constructCertId);
	
	List<ConstructCont> findConstructContCanBeSelectedSupplier(Project project,Long constructCertId,Supplier supplier);
	
}
