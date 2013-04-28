package cn.fyg.pm.domain.fileupload.busifile;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.fyg.pm.domain.shared.BusiCode;

public interface BusifileRepository extends CrudRepository<Busifile, Long> {
	
	List<Busifile> findByBusiCodeAndBusiIdOrderByIdAsc(BusiCode busiCode,Long busiId);

}
