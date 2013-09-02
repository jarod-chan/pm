package cn.fyg.pm.domain.model.Symember;

import org.springframework.data.repository.Repository;

import cn.fyg.pm.domain.model.user.User;

public interface SymemberRepository extends Repository<Symember,Long> {
	
	Symember findByUser(User user);
	
	Symember save(Symember symember);
	
	void delete(Symember symember);

}
