package cn.fyg.pm.interfaces.web.module.trace.designnoti.component;

import cn.fyg.pm.domain.model.design.designnoti.DesignNoti;
import cn.fyg.pm.infrastructure.tool.fmt.Fmt;
import cn.fyg.pm.interfaces.web.shared.component.Transfer;

public class DesignNotiTsf implements Transfer<DesignNoti,DesignNotiSmp>{

	@Override
	public DesignNotiSmp transfer(DesignNoti from) {
		DesignNotiSmp to = new DesignNotiSmp();
		to.setId(from.getId());
		to.setNo(from.getNo());
		to.setBusino(from.getBusino());
		to.setState(from.getState());
		to.setState_name(from.getState().getName());
		to.setCreater_name(from.getCreater()!=null?Fmt.toStr(from.getCreater().getName()):"");
		to.setCreatedate(Fmt.toStr(from.getCreatedate(),"yyyy-MM-dd"));
		return to;
	}

}
