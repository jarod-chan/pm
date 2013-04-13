package cn.fyg.pm.domain.pjmember;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.project.Project;

public interface PjmemberRepository extends Repository<Pjmember,Long> {
	
	Iterable<Pjmember> save(Iterable<Pjmember> PjmemberList);
	
	List<Pjmember> findByProject(Project project);

}
