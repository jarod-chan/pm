package cn.fyg.pm.application.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContFactory;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContItem;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContPU;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContRepository;
import cn.fyg.pm.domain.model.construct.constructcont.ConstructContState;
import cn.fyg.pm.domain.model.construct.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.nogenerator.NoGeneratorBusi;
import cn.fyg.pm.domain.model.nogenerator.NoPatternUnit;
import cn.fyg.pm.domain.model.project.Project;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.shared.repositoryquery.QuerySpec;

@Service("constructContService")
public class ConstructContServiceImpl implements ConstructContService {
	
	@Autowired
	ConstructContRepository constructContRepository;
	@Autowired
	NoGeneratorBusi noGeneratorBusi;

	@Override
	public List<ConstructCont> findAll() {
		return this.constructContRepository.findAll();
	}

	@Override
	@Transactional
	public ConstructCont save(ConstructCont constructCont) {
		if(constructCont.getId()==null){
			this.noGeneratorBusi.generateNextNo(constructCont);
		}
		//TODO 待重构
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
	public List<ConstructCont> findByProjectAndState(Project project,ConstructContState state) {
		return this.constructContRepository.findByConstructKey_ProjectAndState(project,state);
	}

	@Override
	public List<ConstructCont> query(QuerySpec<ConstructCont> querySpec) {
		return this.constructContRepository.query(ConstructCont.class,querySpec);
	}

	@Override
	public List<ConstructCont> findByProjectAndSupplier(Project project,
			Supplier supplier) {
		return this.constructContRepository.findByConstructKey_ProjectAndConstructKey_Supplier(project,supplier);
	}

	@Override
	@Transactional
	public ConstructCont finish(ConstructCont constructCont) {
		if(StringUtils.isBlank(constructCont.getBusino())){			
			NoPatternUnit pu = new ConstructContPU(constructCont);
			this.noGeneratorBusi.generateNextNo(pu);
		}
		return this.constructContRepository.save(constructCont);
	}

}
