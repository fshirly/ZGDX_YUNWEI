/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fable.insightview.platform.common.bpm.activiti.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * xue.antai
 */
public class FlowElement extends BaseElement implements HasExecutionListeners {
	protected String name;
	protected String documentation;
	protected List<ActivitiListener> executionListeners = new ArrayList<ActivitiListener>();
	// FlowNode
	protected List<FlowElement> incomingFlows = new ArrayList<FlowElement>();
	protected List<FlowElement> outgoingFlows = new ArrayList<FlowElement>();
	// Activity
	protected boolean asynchronous;
	protected boolean notExclusive;
	protected String defaultFlow;
	protected boolean forCompensation;
	protected MultiInstanceLoopCharacteristics loopCharacteristics;
	protected IOSpecification ioSpecification;
	protected List<DataAssociation> dataInputAssociations = new ArrayList<DataAssociation>();
	protected List<DataAssociation> dataOutputAssociations = new ArrayList<DataAssociation>();
	protected List<BoundaryEvent> boundaryEvents = new ArrayList<BoundaryEvent>();
	protected String failedJobRetryTimeCycleValue;
	// StartEvent
	protected String initiator;
	protected String formKey;
	protected List<FormProperty> formProperties = new ArrayList<FormProperty>();
	// UserTask
	protected String assignee;
	protected String owner;
	protected String priority;
	protected String dueDate;
	protected String category;
	protected List<String> candidateUsers = new ArrayList<String>();
	protected List<String> candidateGroups = new ArrayList<String>();
	protected List<ActivitiListener> taskListeners = new ArrayList<ActivitiListener>();
	protected String skipExpression;
	protected Map<String, Set<String>> customUserIdentityLinks = new HashMap<String, Set<String>>();
	protected Map<String, Set<String>> customGroupIdentityLinks = new HashMap<String, Set<String>>();
	// Gateway

	//SequeceFlow
	protected String conditionExpression;
	protected String sourceRef;
	protected String targetRef;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public List<ActivitiListener> getExecutionListeners() {
		return executionListeners;
	}

	public void setExecutionListeners(List<ActivitiListener> executionListeners) {
		this.executionListeners = executionListeners;
	}

	public List<FlowElement> getIncomingFlows() {
		return incomingFlows;
	}

	public void setIncomingFlows(List<FlowElement> incomingFlows) {
		this.incomingFlows = incomingFlows;
	}

	public List<FlowElement> getOutgoingFlows() {
		return outgoingFlows;
	}

	public void setOutgoingFlows(List<FlowElement> outgoingFlows) {
		this.outgoingFlows = outgoingFlows;
	}

	public boolean isAsynchronous() {
		return asynchronous;
	}

	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	public boolean isNotExclusive() {
		return notExclusive;
	}

	public void setNotExclusive(boolean notExclusive) {
		this.notExclusive = notExclusive;
	}

	public String getDefaultFlow() {
		return defaultFlow;
	}

	public void setDefaultFlow(String defaultFlow) {
		this.defaultFlow = defaultFlow;
	}

	public boolean isForCompensation() {
		return forCompensation;
	}

	public void setForCompensation(boolean forCompensation) {
		this.forCompensation = forCompensation;
	}

	public MultiInstanceLoopCharacteristics getLoopCharacteristics() {
		return loopCharacteristics;
	}

	public void setLoopCharacteristics(
			MultiInstanceLoopCharacteristics loopCharacteristics) {
		this.loopCharacteristics = loopCharacteristics;
	}

	public IOSpecification getIoSpecification() {
		return ioSpecification;
	}

	public void setIoSpecification(IOSpecification ioSpecification) {
		this.ioSpecification = ioSpecification;
	}

	public List<DataAssociation> getDataInputAssociations() {
		return dataInputAssociations;
	}

	public void setDataInputAssociations(
			List<DataAssociation> dataInputAssociations) {
		this.dataInputAssociations = dataInputAssociations;
	}

	public List<DataAssociation> getDataOutputAssociations() {
		return dataOutputAssociations;
	}

	public void setDataOutputAssociations(
			List<DataAssociation> dataOutputAssociations) {
		this.dataOutputAssociations = dataOutputAssociations;
	}

	public List<BoundaryEvent> getBoundaryEvents() {
		return boundaryEvents;
	}

	public void setBoundaryEvents(List<BoundaryEvent> boundaryEvents) {
		this.boundaryEvents = boundaryEvents;
	}

	public String getFailedJobRetryTimeCycleValue() {
		return failedJobRetryTimeCycleValue;
	}

	public void setFailedJobRetryTimeCycleValue(
			String failedJobRetryTimeCycleValue) {
		this.failedJobRetryTimeCycleValue = failedJobRetryTimeCycleValue;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public List<FormProperty> getFormProperties() {
		return formProperties;
	}

	public void setFormProperties(List<FormProperty> formProperties) {
		this.formProperties = formProperties;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(List<String> candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public List<String> getCandidateGroups() {
		return candidateGroups;
	}

	public void setCandidateGroups(List<String> candidateGroups) {
		this.candidateGroups = candidateGroups;
	}

	public List<ActivitiListener> getTaskListeners() {
		return taskListeners;
	}

	public void setTaskListeners(List<ActivitiListener> taskListeners) {
		this.taskListeners = taskListeners;
	}

	public String getSkipExpression() {
		return skipExpression;
	}

	public void setSkipExpression(String skipExpression) {
		this.skipExpression = skipExpression;
	}

	public Map<String, Set<String>> getCustomUserIdentityLinks() {
		return customUserIdentityLinks;
	}

	public void setCustomUserIdentityLinks(
			Map<String, Set<String>> customUserIdentityLinks) {
		this.customUserIdentityLinks = customUserIdentityLinks;
	}

	public Map<String, Set<String>> getCustomGroupIdentityLinks() {
		return customGroupIdentityLinks;
	}

	public void setCustomGroupIdentityLinks(
			Map<String, Set<String>> customGroupIdentityLinks) {
		this.customGroupIdentityLinks = customGroupIdentityLinks;
	}

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	public String getSourceRef() {
		return sourceRef;
	}

	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	public String getTargetRef() {
		return targetRef;
	}

	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
	
	
}
