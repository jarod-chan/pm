package cn.fyg.pm.domain.constructcont;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface ConstructContRepository extends Repository<ConstructCont, Long> {

	ConstructCont save(ConstructCont constructCont);
	
	List<ConstructCont> findAll();
	
	void delete(Long id);

	ConstructCont findOne(Long id);
}
