package com.fable.insightview.platform.vlan.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * vlan 实体类
 * @author Administrator
 *
 */
@Alias("vlanInfo")
public class VlanInfoBean {

	@NumberGenerator(name = "vlanInfoPK")
	private Integer id; 
	private String vlanNo;
	private String vlanName;
	private String vlanDesc;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVlanNo() {
		return vlanNo;
	}
	public void setVlanNo(String vlanNo) {
		this.vlanNo = vlanNo;
	}
	public String getVlanName() {
		return vlanName;
	}
	public void setVlanName(String vlanName) {
		this.vlanName = vlanName;
	}
	public String getVlanDesc() {
		return vlanDesc;
	}
	public void setVlanDesc(String vlanDesc) {
		this.vlanDesc = vlanDesc;
	}
}
