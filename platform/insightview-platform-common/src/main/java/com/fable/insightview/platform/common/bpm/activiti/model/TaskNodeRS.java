package com.fable.insightview.platform.common.bpm.activiti.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class TaskNodeRS implements Serializable {
	private static final long serialVersionUID = 1447123981460390616L;
	protected String id;
	protected String url;
	protected String owner;
	protected String assignee;
	protected String delegationState;
	// TODO 这里指代原TaskName
	protected String name;
	protected String description;
//	@JsonSerialize(using = DateToStringSerializer.class, as=Date.class)
	protected Date createTime;
//	@JsonSerialize(using = DateToStringSerializer.class, as=Date.class)
	protected Date dueDate;
	protected int priority;
	protected boolean suspended;
	protected String taskDefinitionKey;
	protected String tenantId;
	protected String category;
	protected String formKey;
	  
	// References to other resources
	protected String parentTaskId;
	protected String parentTaskUrl;
	protected String executionId;
	protected String executionUrl;
	protected String processInstanceId;
	protected String processInstanceUrl;
	protected String processDefinitionId;
	protected String processDefinitionUrl;
	/* history task */
	protected Date startTime;
	protected Date endTime;
	/***************用于查询**********************/
	// TODO xat lookUp
	protected boolean lastNode;
	private String processId;
	private String candidateGroup;
	private String candidateUser;
	
	public String getCandidateUser() {
		return candidateUser;
	}
	public void setCandidateUser(String candidateUser) {
		this.candidateUser = candidateUser;
	}
	public String getCandidateGroup() {
		return candidateGroup;
	}
	public void setCandidateGroup(String candidateGroup) {
		this.candidateGroup = candidateGroup;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getDelegationState() {
		return delegationState;
	}
	public void setDelegationState(String delegationState) {
		this.delegationState = delegationState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		if(null==this.createTime) {
			this.createTime = this.startTime;
		}
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public String getParentTaskUrl() {
		return parentTaskUrl;
	}
	public void setParentTaskUrl(String parentTaskUrl) {
		this.parentTaskUrl = parentTaskUrl;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getExecutionUrl() {
		return executionUrl;
	}
	public void setExecutionUrl(String executionUrl) {
		this.executionUrl = executionUrl;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessInstanceUrl() {
		return processInstanceUrl;
	}
	public void setProcessInstanceUrl(String processInstanceUrl) {
		this.processInstanceUrl = processInstanceUrl;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessDefinitionUrl() {
		return processDefinitionUrl;
	}
	public void setProcessDefinitionUrl(String processDefinitionUrl) {
		this.processDefinitionUrl = processDefinitionUrl;
	}
	public boolean isLastNode() {
		return lastNode;
	}
	public void setLastNode(boolean lastNode) {
		this.lastNode = lastNode;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public Date getStartTime() {
		if(null==this.startTime) {
			this.startTime = this.createTime;
		}
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	  
}
