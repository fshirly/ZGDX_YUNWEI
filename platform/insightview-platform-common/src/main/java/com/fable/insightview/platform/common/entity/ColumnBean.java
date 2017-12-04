package com.fable.insightview.platform.common.entity;

//import com.wordnik.swagger.annotations.ApiModel;
//import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author fangang
 * 列对象
 */
//@ApiModel(value = "列对象")
public class ColumnBean
{
    
	//@ApiModelProperty(value = "列ID")
    private String id = "";
    
	//@ApiModelProperty(value = "列名称")
    private String name = "";
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
}
