package cn.fyg.pm.domain.model.contract;

import cn.fyg.pm.domain.model.project.Project;

public class ContractFactory {
	
	public static Contract create(Project project){
		Contract contract = new Contract();
		contract.setProject(project);
		contract.setState(ContractState.signed);
		return contract;
	}

}
