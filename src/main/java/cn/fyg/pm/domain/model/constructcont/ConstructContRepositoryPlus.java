package cn.fyg.pm.domain.model.constructcont;

import java.util.List;

import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.interfaces.web.module.constructcont.query.ContQuery;

public interface ConstructContRepositoryPlus {

	List<ConstructCont> queryList(Project project, ContQuery query);

}