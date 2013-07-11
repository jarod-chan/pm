package cn.fyg.pm.application.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.SpmemberService;
import cn.fyg.pm.domain.model.spmember.Spmember;
import cn.fyg.pm.domain.model.spmember.SpmemberRepository;
import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.domain.model.user.User;

@Service("spmemberService")
public class SpmemberServiceImpl implements SpmemberService {
	
	@Autowired
	SpmemberRepository spmemberRepository;

	@Override
	@Transactional
	public void assignUserSupplier(User user, Supplier supplier) {
		Spmember spmember = this.spmemberRepository.findByUser(user);
		spmember=(spmember==null?new Spmember():spmember);
		spmember.setUser(user);
		spmember.setSupplier(supplier);
		spmemberRepository.save(spmember);
	}

	@Override
	public boolean isUserAssigned(User user) {
		Spmember spmember = this.spmemberRepository.findByUser(user);
		return spmember!=null;
	}

	@Override
	public Supplier getUserSupplier(User user) {
		Spmember spmember = this.spmemberRepository.findByUser(user);
		return spmember.getSupplier();
	}

	@Override
	public Map<User, Supplier> getAllUserSupplier() {
		Iterable<Spmember> spmembers = this.spmemberRepository.findAll();
		HashMap<User, Supplier> userToSupplier = new HashMap<User,Supplier>();
		for (Spmember spmember : spmembers) {
			userToSupplier.put(spmember.getUser(),spmember.getSupplier());
		}
		return userToSupplier;
	}

	@Override
	@Transactional
	public void clearUserSupplier(User user) {
		Spmember spmember = spmemberRepository.findByUser(user);
		if(spmember!=null){
			this.spmemberRepository.delete(spmember);
		}
	}

}
