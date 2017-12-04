package com.fable.insightview.platform.common.bpm.activiti.model;

import java.io.Serializable;
import java.util.Map;

public class DefinitionsRS implements Serializable{

	  private String id;
	  private String url;
	  private String key;
	  private int version;
	  private String name;
	  private String description;
	  private String tenantId;
	  private String deploymentId;
	  private String deploymentUrl;
	  private String resource;
	  private String diagramResource;
	  private String category;
	  private boolean graphicalNotationDefined = false;
	  private boolean suspended = false;
	  private boolean startFormDefined = false;
	  private Map<String, Object> param = null;
	  
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
	  public String getKey() {
	    return key;
	  }
	  public void setKey(String key) {
	    this.key = key;
	  }
	  public int getVersion() {
	    return version;
	  }
	  public void setVersion(int version) {
	    this.version = version;
	  }
	  public String getName() {
	    return name;
	  }
	  public void setName(String name) {
	    this.name = name;
	  }
	  public String getTenantId() {
	    return tenantId;
	  }
	  public void setTenantId(String tenantId) {
	    this.tenantId = tenantId;
	  }
	  public String getDeploymentId() {
	    return deploymentId;
	  }
	  public void setDeploymentId(String deploymentId) {
	    this.deploymentId = deploymentId;
	  }
	  public String getDeploymentUrl() {
	    return deploymentUrl;
	  }
	  public void setDeploymentUrl(String deploymentUrl) {
	    this.deploymentUrl = deploymentUrl;
	  }
	  public String getCategory() {
	    return category;
	  }
	  public void setCategory(String category) {
	    this.category = category;
	  }
	  public void setResource(String resource) {
	    this.resource = resource;
	  }
	  public String getResource() {
	    return resource;
	  }
	  public String getDescription() {
	    return description;
	  }
	  public void setDescription(String description) {
	    this.description = description;
	  }
	  public void setDiagramResource(String diagramResource) {
	    this.diagramResource = diagramResource;
	  }
	  public String getDiagramResource() {
	    return diagramResource;
	  }
	  public void setGraphicalNotationDefined(boolean graphicalNotationDefined) {
	    this.graphicalNotationDefined = graphicalNotationDefined;
	  }
	  public boolean isGraphicalNotationDefined() {
	    return graphicalNotationDefined;
	  }
	  public void setSuspended(boolean suspended) {
	    this.suspended = suspended;
	  }
	  public boolean isSuspended() {
	    return suspended;
	  }
	  public void setStartFormDefined(boolean startFormDefined) {
	    this.startFormDefined = startFormDefined;
	  }
	  public boolean isStartFormDefined() {
	    return startFormDefined;
	  }
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	  
}
