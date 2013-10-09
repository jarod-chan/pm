package cn.fyg.pm.interfaces.web.module.supplier.component;

import cn.fyg.pm.domain.model.supplier.Supplier;
import cn.fyg.pm.interfaces.web.shared.component.Transfer;

public class SupplierTsf implements Transfer<Supplier,SupplierSmp> {

	@Override
	public SupplierSmp transfer(Supplier from) {
		SupplierSmp to = new SupplierSmp();
		to.setId(from.getId());
		to.setNo(from.getNo());
		to.setName(from.getName());
		return to;
	}

}
