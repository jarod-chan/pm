package cn.fyg.pm.domain.model.constructcont;

import java.util.List;

import cn.fyg.pm.domain.shared.QuerySpec;

public interface ConstructContRepositoryPlus {
	
	List<ConstructCont> query(QuerySpec<ConstructCont> querySpec);

}