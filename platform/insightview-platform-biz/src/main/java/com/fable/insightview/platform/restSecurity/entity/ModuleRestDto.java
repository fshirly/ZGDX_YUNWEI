package com.fable.insightview.platform.restSecurity.entity;

//import com.wordnik.swagger.annotations.ApiModel;
//import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author fangang
 * 模块Rest接口DTO
 */
//@ApiModel(value = "模块Rest接口对象")
public class ModuleRestDto {

	/**
     * 主键
     */
    //@ApiModelProperty(value="主键")
	private String id;
    
    /**
     * 父节点ID
     */
    //@ApiModelProperty(value="父节点ID")
	private String pid;
	
    /**
     * 名称
     */
    //@ApiModelProperty(value="名称")
	private String name;
    
    /**
     * 简称
     */
    //@ApiModelProperty(value="简称")
	private String aliasName;
	
    /**
     * 类型（1模块，2Rest接口）
     */
    //@ApiModelProperty(value="类型（1模块，2Rest接口）")
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
