package cn.fyg.pm.application.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.SymemberService;
import cn.fyg.pm.domain.model.role.Role;
import cn.fyg.pm.domain.model.symember.Symember;
import cn.fyg.pm.domain.model.symember.SymemberRepository;
import cn.fyg.pm.domain.model.user.User;

@Service("symemberService")
public class SymemberServiceImpl implements SymemberService {
	
	private static final Logger logger=LoggerFactory.getLogger(SymemberService.class);
	
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

	@Override
	public String roleUser(String roleKey) {
		Role role=new Role();
		role.setKey(roleKey);
		List<Symember> symembers = this.symemberRepository.findByRole(role);
		if(symembers.isEmpty()){
			logger.info("cant find user by roleKey:[%s] ",roleKey );
			return "admin";
		}else if(symembers.size()==1){
			return symembers.get(0).getUser().getKey();
		}else{
			logger.info("find too many user  by roleKey:[%s]", roleKey);
			return "admin";
		}
	}

}
