package cn.fyg.pm.interfaces.web.module.constructcont.flow;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.fyg.pm.interfaces.web.shared.flow.RoleTask;
import cn.fyg.pm.interfaces.web.shared.flow.impl.CommonFlowUtil;

@Component("constructcont")
public class ContUtil extends CommonFlowUtil {
	


	@Override
	public void addRoleTasks(List<RoleTask> reloTasks) {
		/**
		 * 审核人
		 */
		reloTasks.add(new RoleTask("shr",new String[]{"check-azzg","check-zszg","check-szzg","check-jgzg"}));
		
		/**
		 * 核准人
		 */
		reloTasks.add(new RoleTask("hzr",new String[]{"check-xmfzr"}));
		
		/**
		 * 签发人
		 */
		reloTasks.add(new RoleTask("qfr",new String[]{"check-scfgfz","check-szfgfz"}));
	}

}
