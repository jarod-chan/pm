package cn.fyg.pm.interfaces.web.module.process;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.fyg.pm.interfaces.web.shared.constant.FlowConstant;

@Component
public class TaskFacade {
	
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	@Autowired
	RepositoryService repositoryService;
	
	public List<ProcessTaskBean> getProcessTasks(String userKey){
		List<ProcessTaskBean> result=new ArrayList<ProcessTaskBean>();
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userKey).orderByTaskCreateTime().asc().list();
		for (Task task : tasks) {
			ProcessTaskBean processTaskBean=new ProcessTaskBean();
			
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			processTaskBean.setProcessName(processDefinition.getName());
			
			processTaskBean.setTaskName(task.getName());
			processTaskBean.setTaskId(task.getId());

			String formKey= formService.getTaskFormData(task.getId()).getFormKey();
			processTaskBean.setFormKey(formKey);
			
			String executionId = task.getProcessInstanceId();
			processTaskBean.setExecutionId(executionId);
			
			String businessId = getProcessVariable(executionId, FlowConstant.BUSINESS_ID);
			processTaskBean.setBusinessId(businessId);
			
			String businessNo = getProcessVariable(executionId, FlowConstant.BUSINESS_NO);
			processTaskBean.setBusinessNo(businessNo);
			
			String projectId = getProcessVariable(executionId, FlowConstant.PROJECT_ID);
			processTaskBean.setProjectId(projectId);
			
			String projectName = getProcessVariable(executionId, FlowConstant.PROJECT_NAME);
			processTaskBean.setProjectName(projectName);
			
			processTaskBean.setCreateDate(task.getCreateTime());
			
			processTaskBean.setDueDate(task.getDueDate());
			
			result.add(processTaskBean);
		}
		return result;
	}

	/**
	 * 流程变量设置
	 */
	private String getProcessVariable(String executionId,String varName) {
		Object obj=runtimeService.getVariable(executionId,varName);
		return obj==null?"":obj.toString();
	}

}
