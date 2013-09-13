package cn.fyg.pm.domain.model.symember;

import java.util.List;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;

public interface SymemberRepository extends Repository<Symember,Long> {
	
	Symember findByUser(User user);
	
	Symember save(Symember symember);
	
	void delete(Symember symember);

	List<Symember> findByRole(Role role);

}
