package cn.fyg.pm.domain.model.identify.userrole;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.user.User;

public interface UserFnroleRepository extends CrudRepository<UserFnrole, Long> {

	List<UserFnrole> findByUser(User user);

}
