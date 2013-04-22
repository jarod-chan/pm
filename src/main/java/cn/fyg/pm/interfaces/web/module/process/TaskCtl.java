package cn.fyg.pm.interfaces.web.module.process;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.domain.user.User;
import cn.fyg.pm.interfaces.web.module.shared.session.SessionUtil;



@Controller
@RequestMapping("task")
public class TaskCtl {
	
	private static final String PATH = "task/";
	private interface Page {
		String TASK = PATH + "task";
	}
	
	@Autowired
	TaskFacade taskFacade;
	@Autowired
	SessionUtil sessionUtil;

	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList( Map<String,Object> map){
		User user = sessionUtil.getValue("user");
		List<ProcessTaskBean> processTasks = taskFacade.getProcessTasks(user.getKey());
		map.put("processTasks", processTasks);
		return Page.TASK;
	}

}
