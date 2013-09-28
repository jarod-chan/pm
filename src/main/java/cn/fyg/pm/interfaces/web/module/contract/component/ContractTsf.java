package cn.fyg.pm.interfaces.web.module.contract.component;

import cn.fyg.pm.domain.model.contract.general.Contract;
import cn.fyg.pm.interfaces.web.shared.component.Transfer;

public class ContractTsf implements Transfer<Contract, ContractSmp> {

	@Override
	public ContractSmp transfer(Contract from) {
		ContractSmp to = new ContractSmp();
		to.setId(from.getId());
		to.setNo(from.getNo());
		to.setName(from.getName());
		to.setSupplier_name(from.getSupplier().getName());
		to.setSpecialty(from.getSpecialty().getName());
		to.setState(from.getState().getName());
		return to;
	}

}
