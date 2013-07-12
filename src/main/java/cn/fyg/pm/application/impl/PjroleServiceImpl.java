package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fyg.pm.application.PjroleService;
import cn.fyg.pm.domain.model.pjrole.Pjrole;
import cn.fyg.pm.domain.model.pjrole.PjroleRepository;

@Service("pjroleService")
public class PjroleServiceImpl implements PjroleService {
	
	@Autowired
	PjroleRepository pjroleRepository;

	@Override
	public List<Pjrole> findAll() {
		return this.pjroleRepository.findAllOrderBySnAsc();
	}

}
