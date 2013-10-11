package cn.fyg.pm.domain.model.nogenerator2;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.model.nogenerator.NoKey;

public interface NoRecordRepository extends CrudRepository<NoRecord2, NoKey> {
	
	NoRecord2 findByNoKey(NoKey noKey);

}
