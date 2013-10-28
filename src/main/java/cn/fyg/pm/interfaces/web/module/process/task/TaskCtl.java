package cn.fyg.pm.interfaces.web.module.process.task;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.fyg.pm.application.OpinionService;
import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.domain.model.workflow.opinion.Opinion;
import cn.fyg.pm.domain.model.workflow.opinion.OpinionItem;
import cn.fyg.pm.interfaces.web.shared.constant.AppConstant;
import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;



@Controller
@RequestMapping("task")
public class TaskCtl {
	
	private static final String PATH = "process/task/";
	private interface Page {
		String TASK = PATH +"task";
	}
	
	@Autowired
	TaskAssembler taskAssembler;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	OpinionService opinionService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList( Map<String,Object> map){
		User user = sessionUtil.getValue("user");
		List<TaskDto> taskDtos = taskAssembler.getProcessTasks(user.getKey());
		map.put("taskDtos", taskDtos);
		return Page.TASK;
	}
	
	 
	//公共提交方法
	@RequestMapping(value="check/commit",method=RequestMethod.POST)
	public String checkCommit(Opinion opinion,RedirectAttributes redirectAttributes,@RequestParam(value="taskId",required=false)String taskId,@RequestParam(value="ignoreItem")boolean ignoreItem){
		User user = sessionUtil.getValue("user");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		opinion.setTaskKey(task.getTaskDefinitionKey());
		opinion.setTaskName(task.getName());
		opinion.setUserKey(user.getKey());
		opinion.setUserName(user.getName());
		if(ignoreItem||opinion.getOpinionItems()==null){
			opinion.setOpinionItems(new ArrayList<OpinionItem>());
		}

		doCheckCommit(user, task, opinion);
		redirectAttributes
			.addFlashAttribute(AppConstant.MESSAGE_NAME,info("任务完成"));
		return "redirect:/task/list";
	}

	@Transactional
	public void doCheckCommit(User user, Task task, Opinion opinion) {
		opinionService.append(opinion);
		runtimeService.setVariable(task.getProcessInstanceId(), FlowConstant.OPINION,opinion.getResult().val());
		runtimeService.setVariable(task.getProcessInstanceId(), FlowConstant.LAST_USERKEY,user.getKey());
		taskService.complete(task.getId());
	}
	
}
