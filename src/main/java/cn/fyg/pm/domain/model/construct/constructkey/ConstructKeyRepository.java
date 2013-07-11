package cn.fyg.pm.domain.model.construct.constructkey;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.project.Project;

public interface ConstructKeyRepository extends CrudRepository<ConstructKey,Long> {

	List<ConstructKey> findByProjectOrderByIdAsc(Project project);

}
