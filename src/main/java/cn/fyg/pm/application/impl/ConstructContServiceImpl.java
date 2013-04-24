package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructContService;
import cn.fyg.pm.domain.constructcont.ConstructCont;
import cn.fyg.pm.domain.constructcont.ConstructContFactory;
import cn.fyg.pm.domain.constructcont.ConstructContItem;
import cn.fyg.pm.domain.constructcont.ConstructContRepository;
import cn.fyg.pm.domain.constructkey.ConstructKey;
import cn.fyg.pm.domain.user.User;

@Service
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
	public ConstructCont create(User creater) {
		return ConstructContFactory.create(creater);
	}

}
