package cn.fyg.pm.domain.model.spmember;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.user.User;

public interface SpmemberRepository extends CrudRepository<Spmember, Long> {

	Spmember findByUser(User user);

}
