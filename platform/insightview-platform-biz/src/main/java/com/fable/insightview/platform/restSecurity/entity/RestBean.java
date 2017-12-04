package com.fable.insightview.platform.restSecurity.entity;

//import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author fangang
 * Rest接口描述Bean
 */
public class RestBean {

	/**
     * 主键
     */
    //@ApiModelProperty(value="主键")
    private String id;
    
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
     * url
     */
    //@ApiModelProperty(value="url")
    private String url;
    
    /**
     * 父ID
     */
    //@ApiModelProperty(value="父ID")
    private String pid;
    
    /**
     * 类型（1包，2类，3方法）
     */
    //@ApiModelProperty(value="类型（1包，2类，3方法）")
    private String type;
    
    /**
     * 说明
     */
    //@ApiModelProperty(value="说明")
    private String note;

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * @param aliasName
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/**
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
}
