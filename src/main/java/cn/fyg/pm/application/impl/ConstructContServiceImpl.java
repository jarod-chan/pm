package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.constructcont.ConstructContFactory;
import cn.fyg.pm.domain.model.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.constructcont.ConstructContRepository;
import cn.fyg.pm.domain.model.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.user.User;

@Service("constructContService")
public class ConstructContServiceImpl implements ConstructContService {
	
	@Autowired
	ConstructContRepository constructContRepository;

	@Override
	public List<ConstructCont> findAll() {
		return this.constructContRepository.findAll();
	}

	@Override
	@Transactional
	public ConstructCont save(ConstructCont constructCont) {
		for(ConstructContItem constructContItem:constructCont.getConstructContItems()){
			constructContItem.setConstructCont(constructCont);
		}
		return this.constructContRepository.save(constructCont);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.constructContRepository.delete(id);
	}

	@Override
	public ConstructCont find(Long id) {
		return this.constructContRepository.findOne(id);
	}

	@Override
	public ConstructCont findByConstructKey(ConstructKey constructKey) {
		return this.constructContRepository.findByConstructKey(constructKey);
	}

	@Override
	public ConstructCont create(User creater,Project project, ConstructContState state) {
		return ConstructContFactory.create(creater,project,state);
	}

	@Override
	public List<ConstructCont> findByProject(Project project) {
		return this.constructContRepository.findByConstructKey_Project(project);
	}

}
