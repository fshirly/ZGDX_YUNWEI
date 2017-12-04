package com.fable.insightview.platform.ipmanager.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class IPManSubNetInfoBean {
	//子网id
	@NumberGenerator(name = "ipManSubNetInfoPK")
	private Integer subNetId;
	//网络类型
	private Integer subNetType;
	//子网ip
	private String ipAddress;
	//子网掩码
	private String subNetMark;
	//占用地址数
	private Integer usedNum;
	//预留地址数
	private Integer preemptNum;
	//空闲地址数
	private Integer freeNum;
	//子网容量
	private Integer totalNum;
	//网络地址
	private String netAddress;
	//广播地址
	private String broadCast;
	//描述
	private String descr;
	//是否是拆分后的子网段 0：不是 1 是
	private Integer isSplit;
	//网关
	private String gateway;
	//DNS服务器地址
	private String dnsAddress;
	
	private Integer parentId;
	private String startIp;
	private String endIp;
	private String deptName;
	private String subNetTypeName;
	private int isNetAddressFree;
	private int isBroadCastFree;
	private String isNetAddressFreeStr;
	private String isBroadCastFreeStr;
	private Integer deptId;

	public Integer getSubNetId() {
		return subNetId;
	}

	public void setSubNetId(Integer subNetId) {
		this.subNetId = subNetId;
	}

	public Integer getSubNetType() {
		return subNetType;
	}

	public void setSubNetType(Integer subNetType) {
		this.subNetType = subNetType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSubNetMark() {
		return subNetMark;
	}

	public void setSubNetMark(String subNetMark) {
		this.subNetMark = subNetMark;
	}

	public Integer getUsedNum() {
		return usedNum;
	}

	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	public Integer getPreemptNum() {
		return preemptNum;
	}

	public void setPreemptNum(Integer preemptNum) {
		this.preemptNum = preemptNum;
	}

	public Integer getFreeNum() {
		return freeNum;
	}

	public void setFreeNum(Integer freeNum) {
		this.freeNum = freeNum;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public String getNetAddress() {
		return netAddress;
	}

	public void setNetAddress(String netAddress) {
		this.netAddress = netAddress;
	}

	public String getBroadCast() {
		return broadCast;
	}

	public void setBroadCast(String broadCast) {
		this.broadCast = broadCast;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getStartIp() {
		return startIp;
	}

	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}

	public String getEndIp() {
		return endIp;
	}

	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSubNetTypeName() {
		return subNetTypeName;
	}

	public void setSubNetTypeName(String subNetTypeName) {
		this.subNetTypeName = subNetTypeName;
	}

	public int getIsNetAddressFree() {
		return isNetAddressFree;
	}

	public void setIsNetAddressFree(int isNetAddressFree) {
		this.isNetAddressFree = isNetAddressFree;
	}

	public int getIsBroadCastFree() {
		return isBroadCastFree;
	}

	public void setIsBroadCastFree(int isBroadCastFree) {
		this.isBroadCastFree = isBroadCastFree;
	}

	public String getIsNetAddressFreeStr() {
		return isNetAddressFreeStr;
	}

	public void setIsNetAddressFreeStr(String isNetAddressFreeStr) {
		this.isNetAddressFreeStr = isNetAddressFreeStr;
	}

	public String getIsBroadCastFreeStr() {
		return isBroadCastFreeStr;
	}

	public void setIsBroadCastFreeStr(String isBroadCastFreeStr) {
		this.isBroadCastFreeStr = isBroadCastFreeStr;
	}

	public Integer getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getDnsAddress() {
		return dnsAddress;
	}

	public void setDnsAddress(String dnsAddress) {
		this.dnsAddress = dnsAddress;
	}
	
}
