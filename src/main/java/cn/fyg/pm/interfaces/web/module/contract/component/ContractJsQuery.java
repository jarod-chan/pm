package cn.fyg.pm.interfaces.web.module.contract.component;

import cn.fyg.pm.domain.model.contract.general.ContractType;

public class ContractJsQuery {
	
	private String no;
	
	private ContractType contractType;//合同类型

	private int page;//页码
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public ContractType getContractType() {
		return contractType;
	}

	public void setContractType(ContractType contractType) {
		this.contractType = contractType;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	
}
