package cn.fyg.pm.domain.model.pjmember;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

public interface PjmemberRepository extends Repository<Pjmember,Long> {
	
	Iterable<Pjmember> save(Iterable<Pjmember> PjmemberList);
	
	List<Pjmember> findByProject(Project project);

	void delete(Iterable<Pjmember> entities);
	
	List<Pjmember> findByUser(User user);
}
