package cn.fyg.pm.domain.model.construct.constructcont;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface ConstructContRepository extends Repository<ConstructCont, Long>, RepositoryQuery<ConstructCont> {

	ConstructCont save(ConstructCont constructCont);
	
	List<ConstructCont> findAll();
	
	void delete(Long id);

	ConstructCont findOne(Long id);

	ConstructCont findByConstructKey(ConstructKey constructKey);

	List<ConstructCont> findByConstructKey_ProjectAndState(Project project,ConstructContState state);

	List<ConstructCont> findByConstructKey_ProjectAndConstructKey_Supplier(Project project, Supplier supplier);
}
