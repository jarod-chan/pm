package cn.fyg.pm.domain.model.contract.purchase;

import cn.fyg.pm.domain.model.contract.ContractState;
import cn.fyg.pm.domain.model.project.Project;

public class ContractMeterFactory {
	
	public static ContractMeter create(Project project){
		ContractMeter contractMeter = new ContractMeter();
		contractMeter.setProject(project);
		contractMeter.setState(ContractState.signed);
		return contractMeter;
	}

}
