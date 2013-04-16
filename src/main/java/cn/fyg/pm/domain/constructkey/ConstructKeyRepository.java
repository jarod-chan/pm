package cn.fyg.pm.domain.constructkey;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.project.Project;

public interface ConstructKeyRepository extends CrudRepository<ConstructKey,Long> {

	List<ConstructKey> findByProjectOrderByIdAsc(Project project);

}
