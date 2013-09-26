package cn.fyg.pm.interfaces.web.module.contract.component;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.fyg.pm.domain.model.contract.general.Contract;

import com.google.common.collect.Lists;

public class ContractFacade {
	
	public static  PageData<ContractSmp> transfer(Page<Contract> page){
		List<Contract> contracts = page.getContent();
		List<ContractSmp> contractSmps = Lists.newArrayList();
		for (Contract contract : contracts) {
			ContractSmp contractSmp = new ContractSmp();
			contractSmp.setId(contract.getId());
			contractSmp.setNo(contract.getNo());
			contractSmp.setName(contract.getName());
			contractSmp.setSupplier_name(contract.getSupplier().getName());
			contractSmp.setSpecialty(contract.getSpecialty().getName());
			contractSmp.setState(contract.getState().getName());
			contractSmps.add(contractSmp);
		}
		PageData<ContractSmp> pageData=new PageData<ContractSmp>();
		pageData.setContent(contractSmps);
		pageData.setNumber(page.getNumber());
		pageData.setLastPage(page.isLastPage());
		return pageData;
	}

}
