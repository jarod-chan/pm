package cn.fyg.pm.interfaces.web.module.process.trace;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Controller
@RequestMapping("trace")
public class TraceCtl {
	
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ProcessEngineFactoryBean processEngine;
	
	@RequestMapping(value = "{executionId}")
	public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)throws Exception {
		 //TODO 并行节点出现问题
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
		InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
		
		writeStreamToResponse(response, imageStream);
	}
	
	@RequestMapping(value = "{processDefKey}/{businessId}")
	public void trace(@PathVariable("processDefKey") String processDefKey,@PathVariable("businessId") Long businessId,HttpServletResponse response)throws Exception {
		 //TODO 并行节点出现问题
		List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
				.processDefinitionKey(processDefKey)
				.variableValueEquals(FlowConstant.BUSINESS_ID, businessId)
				.orderByProcessInstanceId().desc()
				.list();
		if(processInstanceList.isEmpty()){
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);//返回301状态码
			response.setHeader("Location","/pm/404");
			return;
		}
		ProcessInstance processInstance=processInstanceList.get(0);
		InputStream imageStream = getImageStream(processInstance);
		writeStreamToResponse(response, imageStream);
	}

	public InputStream getImageStream(ProcessInstance processInstance) {
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstance.getId());
		
		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
		
		InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
		return imageStream;
	}

	// 输出资源内容到相应对象
	public void writeStreamToResponse(HttpServletResponse response,
			InputStream imageStream) throws IOException {
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

}
