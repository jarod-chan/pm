package cn.fyg.pm.application;

import java.util.List;

import cn.fyg.pm.application.common.ServiceQuery;
import cn.fyg.pm.domain.model.contract.purchase.ContractMeter;
import cn.fyg.pm.domain.model.nogenerator.NoNotLastException;
import cn.fyg.pm.domain.model.project.Project;

public interface ContractMeterService extends ServiceQuery<ContractMeter> {


	List<ContractMeter> findByProject(Project project);

	ContractMeter find(Long id);

	ContractMeter create(Project project);

	ContractMeter save(ContractMeter contractMeter);

	void delete(Long contractMeterId)throws NoNotLastException;

}
