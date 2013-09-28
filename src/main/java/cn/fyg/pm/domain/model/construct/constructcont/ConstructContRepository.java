package cn.fyg.pm.domain.model.construct.constructcont;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.shared.repositoryquery.RepositoryQuery;

public interface ConstructContRepository extends Repository<ConstructCont, Long>,JpaSpecificationExecutor<ConstructCont>, RepositoryQuery<ConstructCont> {

	ConstructCont save(ConstructCont constructCont);
	
	List<ConstructCont> findAll();
	
	void delete(Long id);

	ConstructCont findOne(Long id);

	ConstructCont findByConstructKey(ConstructKey constructKey);

	 @Query("select a from ConstructCont a where a.constructKey.project=:project and a.state=:state and (a.constructKey.constructcert_id is null or a.constructKey.constructcert_id=:constructcert_id) order by a.id desc") 
	List<ConstructCont> findConstructContCanBeSelected(@Param("project")Project project,@Param("state")ConstructContState state,@Param("constructcert_id")Long constructcert_id);

	 @Query("select a from ConstructCont a " +
	 		"where a.constructKey.project=:project " +
	 		"and a.state=:state " +
	 		"and a.constructKey.supplier=:supplier "+
	 		"and (a.constructKey.constructcert_id is null " +
	 			"or a.constructKey.constructcert_id=:constructcert_id" +
	 		") order by a.id desc") 
	List<ConstructCont> findConstructContCanBeSelectedSupplier(@Param("project")Project project,@Param("state")ConstructContState state,@Param("constructcert_id")Long constructcert_id,@Param("supplier")Supplier supplier);
}
