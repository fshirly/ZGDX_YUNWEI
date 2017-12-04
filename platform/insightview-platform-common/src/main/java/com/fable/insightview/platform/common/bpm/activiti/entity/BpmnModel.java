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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fable.insightview.platform.common.bpm.activiti.entity.Process;

import org.apache.commons.lang3.StringUtils;


/**
 * @author xue.antai
 */
public class BpmnModel {
  
  protected Map<String, List<ExtensionAttribute>> definitionsAttributes = new LinkedHashMap<String, List<ExtensionAttribute>>();
	protected List<Process> processes = new ArrayList<Process>();
	protected Map<String, GraphicInfo> locationMap = new LinkedHashMap<String, GraphicInfo>();
	protected Map<String, GraphicInfo> labelLocationMap = new LinkedHashMap<String, GraphicInfo>();
	protected Map<String, List<GraphicInfo>> flowLocationMap = new LinkedHashMap<String, List<GraphicInfo>>();
	protected List<Signal> signals = new ArrayList<Signal>();
	protected List<Pool> pools = new ArrayList<Pool>();
	protected List<Import> imports = new ArrayList<Import>();
	protected List<Interface> interfaces = new ArrayList<Interface>();
	protected List<Artifact> globalArtifacts = new ArrayList<Artifact>();
	protected String targetNamespace;
	protected List<String> userTaskFormTypes;
	protected List<String> startEventFormTypes;
	protected Map<String, String> errors = new LinkedHashMap<String, String>();
	protected Map<String, ItemDefinition> itemDefinitions = new LinkedHashMap<String, ItemDefinition>();
	protected Collection<Message> messages;
	protected Map<String, DataStore> dataStores = new LinkedHashMap<String, DataStore>();
	protected Map<String, String> namespaces = new LinkedHashMap<String, String>();
	protected Map<String, MessageFlow> messageFlowMap = new LinkedHashMap<String, MessageFlow>();
//	mainProcess
//	protected int nextFlowIdCounter = 1;
	public Map<String, List<ExtensionAttribute>> getDefinitionsAttributes() {
		return definitionsAttributes;
	}
	public void setDefinitionsAttributes(
			Map<String, List<ExtensionAttribute>> definitionsAttributes) {
		this.definitionsAttributes = definitionsAttributes;
	}
	public List<Process> getProcesses() {
		return processes;
	}
	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}
	public Map<String, GraphicInfo> getLocationMap() {
		return locationMap;
	}
	public void setLocationMap(Map<String, GraphicInfo> locationMap) {
		this.locationMap = locationMap;
	}
	public Map<String, GraphicInfo> getLabelLocationMap() {
		return labelLocationMap;
	}
	public void setLabelLocationMap(Map<String, GraphicInfo> labelLocationMap) {
		this.labelLocationMap = labelLocationMap;
	}
	public Map<String, List<GraphicInfo>> getFlowLocationMap() {
		return flowLocationMap;
	}
	public void setFlowLocationMap(Map<String, List<GraphicInfo>> flowLocationMap) {
		this.flowLocationMap = flowLocationMap;
	}
	public List<Signal> getSignals() {
		return signals;
	}
	public void setSignals(List<Signal> signals) {
		this.signals = signals;
	}
	public List<Pool> getPools() {
		return pools;
	}
	public void setPools(List<Pool> pools) {
		this.pools = pools;
	}
	public List<Import> getImports() {
		return imports;
	}
	public void setImports(List<Import> imports) {
		this.imports = imports;
	}
	public List<Interface> getInterfaces() {
		return interfaces;
	}
	public void setInterfaces(List<Interface> interfaces) {
		this.interfaces = interfaces;
	}
	public List<Artifact> getGlobalArtifacts() {
		return globalArtifacts;
	}
	public void setGlobalArtifacts(List<Artifact> globalArtifacts) {
		this.globalArtifacts = globalArtifacts;
	}
	public String getTargetNamespace() {
		return targetNamespace;
	}
	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}
	public List<String> getUserTaskFormTypes() {
		return userTaskFormTypes;
	}
	public void setUserTaskFormTypes(List<String> userTaskFormTypes) {
		this.userTaskFormTypes = userTaskFormTypes;
	}
	public List<String> getStartEventFormTypes() {
		return startEventFormTypes;
	}
	public void setStartEventFormTypes(List<String> startEventFormTypes) {
		this.startEventFormTypes = startEventFormTypes;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	public Map<String, ItemDefinition> getItemDefinitions() {
		return itemDefinitions;
	}
	public void setItemDefinitions(Map<String, ItemDefinition> itemDefinitions) {
		this.itemDefinitions = itemDefinitions;
	}
	public Collection<Message> getMessages() {
		return messages;
	}
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
	public Map<String, DataStore> getDataStores() {
		return dataStores;
	}
	public void setDataStores(Map<String, DataStore> dataStores) {
		this.dataStores = dataStores;
	}
	public Map<String, String> getNamespaces() {
		return namespaces;
	}
	public void setNamespaces(Map<String, String> namespaces) {
		this.namespaces = namespaces;
	}
	public Map<String, MessageFlow> getMessageFlowMap() {
		return messageFlowMap;
	}
	public void setMessageFlowMap(Map<String, MessageFlow> messageFlowMap) {
		this.messageFlowMap = messageFlowMap;
	}
	
}
