package com.fable.insightview.platform.common.bpm.activiti.model;

/**
 * 节点信息相关
 * @author xue.antai
 *
 */
public class NodeInfoRS {
	private String nodeDefKey;
	private String nodeName;
	private String executor;
	private Boolean isGroupTask = false;
	private String candidateGroup;
	private String candidateUser;
	private NodeInfoRS nextNode;
	
	public NodeInfoRS getNextNode() {
		return nextNode;
	}
	public void setNextNode(NodeInfoRS nextNode) {
		this.nextNode = nextNode;
	}
	public String getNodeDefKey() {
		return nodeDefKey;
	}
	public void setNodeDefKey(String nodeDefKey) {
		this.nodeDefKey = nodeDefKey;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	public Boolean getIsGroupTask() {
		return isGroupTask;
	}
	public void setIsGroupTask(Boolean isGroupTask) {
		this.isGroupTask = isGroupTask;
	}
	public String getCandidateGroup() {
		return candidateGroup;
	}
	public void setCandidateGroup(String candidateGroup) {
		this.candidateGroup = candidateGroup;
	}
	public String getCandidateUser() {
		return candidateUser;
	}
	public void setCandidateUser(String candidateUser) {
		this.candidateUser = candidateUser;
	}
	
}
