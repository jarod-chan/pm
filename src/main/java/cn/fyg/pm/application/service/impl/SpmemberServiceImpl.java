package cn.fyg.pm.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.service.SpmemberService;
import cn.fyg.pm.domain.model.spmember.Spmember;
import cn.fyg.pm.domain.model.spmember.SpmemberRepository;

@Service("spmemberService")
public class SpmemberServiceImpl implements SpmemberService {
	
	@Autowired
	SpmemberRepository spmemberRepository;

	@Override
	public List<Spmember> findAll() {
		Iterable<Spmember> spmembers = this.spmemberRepository.findAll();
		return spmembers!=null?(List<Spmember>)spmembers:new ArrayList<Spmember>();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.spmemberRepository.delete(id);
	}

	@Override
	@Transactional
	public Spmember save(Spmember spmember) {
		return this.spmemberRepository.save(spmember);
	}

}
