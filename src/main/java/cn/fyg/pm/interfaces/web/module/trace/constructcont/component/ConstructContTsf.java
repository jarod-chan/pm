package cn.fyg.pm.interfaces.web.module.trace.constructcont.component;

import cn.fyg.pm.domain.model.construct.constructcont.ConstructCont;
import cn.fyg.pm.infrastructure.tool.fmt.Fmt;
import cn.fyg.pm.interfaces.web.shared.component.Transfer;

public class ConstructContTsf implements Transfer<ConstructCont, ConstructContSmp> {

	@Override
	public ConstructContSmp transfer(ConstructCont from) {
		ConstructContSmp to = new ConstructContSmp();
		to.setConstructKey_id(from.getConstructKey().getId());
		to.setId(from.getId());
		to.setNo(from.getNo());
		to.setContract_name(from.getConstructKey().getContract().getName());
		to.setSupplier_name(from.getConstructKey().getSupplier().getName());
		to.setContract_specialty(from.getConstructKey().getContract().getSpecialty().getName());
		to.setTolsum(Fmt.toStr(from.getTolsum(),"0.00"));
		to.setCreater_name(from.getCreater()!=null?Fmt.toStr(from.getCreater().getName()):"");
		to.setCreatedate(Fmt.toStr(from.getCreatedate(),"yyyy-MM-dd"));
		return to;
	}

}
