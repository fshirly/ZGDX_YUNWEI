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
import java.util.Collection;
import java.util.List;

/**
 * @author xue.antai
 */
public class Process extends BaseElement {
	protected String name;
	protected boolean executable = true;
	protected String documentation;
	protected IOSpecification ioSpecification;
	protected List<ActivitiListener> executionListeners = new ArrayList<ActivitiListener>();
	protected List<Lane> lanes = new ArrayList<Lane>();
	protected List<ValuedDataObject> dataObjects = new ArrayList<ValuedDataObject>();
	protected List<String> candidateStarterUsers = new ArrayList<String>();
	protected List<String> candidateStarterGroups = new ArrayList<String>();
	protected List<EventListener> eventListeners = new ArrayList<EventListener>();
	protected List<Artifact> artifactList = new ArrayList<Artifact>();
	protected List<FlowElement> flowElements = new ArrayList<FlowElement>();

	public Process() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExecutable() {
		return executable;
	}

	public void setExecutable(boolean executable) {
		this.executable = executable;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public IOSpecification getIoSpecification() {
		return ioSpecification;
	}

	public void setIoSpecification(IOSpecification ioSpecification) {
		this.ioSpecification = ioSpecification;
	}

	public List<ActivitiListener> getExecutionListeners() {
		return executionListeners;
	}

	public void setExecutionListeners(List<ActivitiListener> executionListeners) {
		this.executionListeners = executionListeners;
	}

	public List<Lane> getLanes() {
		return lanes;
	}

	public void setLanes(List<Lane> lanes) {
		this.lanes = lanes;
	}

	public List<ValuedDataObject> getDataObjects() {
		return dataObjects;
	}

	public void setDataObjects(List<ValuedDataObject> dataObjects) {
		this.dataObjects = dataObjects;
	}

	public List<String> getCandidateStarterUsers() {
		return candidateStarterUsers;
	}

	public void setCandidateStarterUsers(List<String> candidateStarterUsers) {
		this.candidateStarterUsers = candidateStarterUsers;
	}

	public List<String> getCandidateStarterGroups() {
		return candidateStarterGroups;
	}

	public void setCandidateStarterGroups(List<String> candidateStarterGroups) {
		this.candidateStarterGroups = candidateStarterGroups;
	}

	public List<EventListener> getEventListeners() {
		return eventListeners;
	}

	public void setEventListeners(List<EventListener> eventListeners) {
		this.eventListeners = eventListeners;
	}

	public List<Artifact> getArtifactList() {
		return artifactList;
	}

	public void setArtifactList(List<Artifact> artifactList) {
		this.artifactList = artifactList;
	}

	public List<FlowElement> getFlowElements() {
		return flowElements;
	}

	public void setFlowElements(List<FlowElement> flowElements) {
		this.flowElements = flowElements;
	}

}
