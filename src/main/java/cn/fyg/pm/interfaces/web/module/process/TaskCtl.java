package cn.fyg.pm.interfaces.web.module.process;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.fyg.pm.domain.model.user.User;
import cn.fyg.pm.interfaces.web.shared.session.SessionUtil;



@Controller
@RequestMapping("task")
public class TaskCtl {
	
	private static final String PATH = "task/";
	private interface Page {
		String FIRST = PATH + "first";
		String TASK = PATH +"task";
		String CONTRACTOR = PATH + "contractor";
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
	
	@RequestMapping(value="first",method=RequestMethod.GET)
	public String toFirst(){
		return Page.FIRST;
		
	}
	
	@RequestMapping(value="contractor",method=RequestMethod.GET)
	public String toContractor(){
		return Page.CONTRACTOR;
	}

	@RequestMapping(value="list",method=RequestMethod.GET)
	public String toList( Map<String,Object> map){
		User user = sessionUtil.getValue("user");
		List<ProcessTaskBean> processTasks = taskFacade.getProcessTasks(user.getKey());
		map.put("processTasks", processTasks);
		return Page.TASK;
	}
	

	 @RequestMapping(value = "trace/{executionId}")
	public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)throws Exception {
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

}
