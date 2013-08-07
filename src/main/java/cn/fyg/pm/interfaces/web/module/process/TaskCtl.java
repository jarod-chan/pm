package cn.fyg.pm.interfaces.web.module.process;

import static cn.fyg.pm.interfaces.web.shared.message.Message.info;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private static final String PATH = "task/";
	private interface Page {
		String TASK = PATH +"task";
	}
	
	@Autowired
	TaskFacade taskFacade;
	@Autowired
	SessionUtil sessionUtil;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ProcessEngineFactoryBean processEngine;
	@Autowired
	TaskService taskService;
	@Autowired
	OpinionService opinionService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList( Map<String,Object> map){
		User user = sessionUtil.getValue("user");
		List<ProcessTaskBean> processTasks = taskFacade.getProcessTasks(user.getKey());
		map.put("processTasks", processTasks);
		return Page.TASK;
	}
	

	 @RequestMapping(value = "trace/{executionId}")
	public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)throws Exception {
		 //TODO 并行节点出现问题
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		
		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
		
		InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
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
		if(ignoreItem){
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
