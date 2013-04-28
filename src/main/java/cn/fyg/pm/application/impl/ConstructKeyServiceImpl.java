package cn.fyg.pm.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fyg.pm.application.ConstructKeyService;
import cn.fyg.pm.domain.model.constructkey.ConstructKey;
import cn.fyg.pm.domain.model.constructkey.ConstructKeyRepository;
import cn.fyg.pm.domain.model.project.Project;

@Service
public class ConstructKeyServiceImpl implements ConstructKeyService {

	@Autowired
	ConstructKeyRepository constructKeyRepository;
	
	@Override
	public List<ConstructKey> findByProject(Project project) {
		return constructKeyRepository.findByProjectOrderByIdAsc(project);
	}

	@Override
	@Transactional
	public ConstructKey save(ConstructKey constructKey) {
		return constructKeyRepository.save(constructKey);
	}

	@Override
	@Transactional
	public void delete(Long constructKeyID) {
		constructKeyRepository.delete(constructKeyID);
	}

	@Override
	public ConstructKey find(Long constructKeyId) {
		return constructKeyRepository.findOne(constructKeyId);
	}

}
