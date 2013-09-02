package cn.fyg.pm.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.SymemberService;
import cn.fyg.pm.domain.model.Symember.Symember;
import cn.fyg.pm.domain.model.Symember.SymemberRepository;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.user.User;

@Service("symemberService")
public class SymemberServiceImpl implements SymemberService {
	
	@Autowired
	SymemberRepository symemberRepository;

	@Override
	@Transactional
	public void setUserRole(User user, Role role) {
		Symember symember = this.symemberRepository.findByUser(user);
		if(symember!=null){
			symember.setRole(role);
			this.symemberRepository.save(symember);
			return;
		}
		Symember newSymember=new Symember();
		newSymember.setUser(user);
		newSymember.setRole(role);
		this.symemberRepository.save(newSymember);
	}

	@Override
	public Role findByUser(User user) {
		Symember symember = this.symemberRepository.findByUser(user);
		if(symember!=null){
			return symember.getRole();
		}
		return null;
	}

	@Override
	@Transactional
	public void removeUserRole(User user) {
		Symember symember = this.symemberRepository.findByUser(user);
		if(symember!=null){
			this.symemberRepository.delete(symember);
		}
	}

}
