package com.fable.insightview.platform.common.entity;


/**
 * treeDictionary 查询条件
 * 
 * @author zhouwei
 */
//@ApiModel(value = "treeDictionary查询条件")
public class TreeDictionaryQueryBean {
	
	/**
	 * 名称
	 */
	//@ApiModelProperty(value = "listview名称（英文）")
	private String listviewName;
	
	/**
	 * type
	 */
	//@ApiModelProperty(value = "类型")
	private String type;
	
	/**
	 * parentId
	 */
	//@ApiModelProperty(value = "父节点Id")
	private String pid;
	
	/**
	 * code 
	 * @return
	 */
	//@ApiModelProperty(value = "父节点代码")
	private String code;

	public String getListviewName() {
		return listviewName;
	}

	public void setListviewName(String listviewName) {
		this.listviewName = listviewName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
}