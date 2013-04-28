package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructCertService;
import cn.fyg.pm.domain.model.constructcert.ConstructCert;
import cn.fyg.pm.domain.model.constructcert.ConstructCertFactory;
import cn.fyg.pm.domain.model.constructcert.ConstructCertRepository;
import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.user.User;

@Service
public class ConstructCertServiceImpl implements ConstructCertService {
	
	@Autowired
	ConstructCertRepository constructCertRepository;

	@Override
	public List<ConstructCert> findAll() {
		return constructCertRepository.findAll();
	}

	@Override
	@Transactional
	public ConstructCert save(ConstructCert constructCert) {
		return constructCertRepository.save(constructCert);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.constructCertRepository.delete(id);
	}

	@Override
	public ConstructCert findByConstructKey(ConstructKey constructKey) {
		return this.constructCertRepository.findByConstructKey(constructKey);
	}

	@Override
	public ConstructCert find(Long certid) {
		return this.constructCertRepository.findOne(certid);
	}

	@Override
	public ConstructCert create(User user) {
		return ConstructCertFactory.create(user);
	}

}
