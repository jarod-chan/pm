package cn.fyg.pm.interfaces.web.module.process.task;

import java.util.Date;


public class TaskDto {
	
	private String executionId;//流程实例id
	
	private String processName;//流程名称
	
	private String taskName;//任务名称
	
	private String taskId;//任务id
	
	private String businessId;//业务id
	
	private String businessNo;//序号
	
	private String projectId;//项目id
	
	private String projectName;//项目名称
	
	private Date startTime;//流程开始时间
	
	private TimeDuration startDuration;//已开始时间
	
	private Date createDate;//任务创建日期
	
	private TimeDuration createDuration;//已接收时间
	
	private Date dueDate;//任务期限

	private String formKey;//任务页面
	
	
	
	public TimeDuration getStartDuration() {
		return startDuration;
	}

	public void setStartDuration(TimeDuration startDuration) {
		this.startDuration = startDuration;
	}

	public TimeDuration getCreateDuration() {
		return createDuration;
	}

	public void setCreateDuration(TimeDuration createDuration) {
		this.createDuration = createDuration;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
}
