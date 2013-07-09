package cn.fyg.pm.domain.model.nogenerator;

import org.springframework.data.repository.CrudRepository;

public interface NoRecordRepository extends CrudRepository<NoRecord, NoKey> {
	
	NoRecord findByNoKey(NoKey noKey);

}
