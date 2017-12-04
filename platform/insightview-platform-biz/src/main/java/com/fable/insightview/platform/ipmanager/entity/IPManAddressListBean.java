package com.fable.insightview.platform.ipmanager.entity;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

public class IPManAddressListBean {
	@NumberGenerator(name = "ipManAddressListPK")
	private Integer id;
	// ip地址
	private String ipAddress;
	// 所属子网的id
	private Integer subNetId;
	// 部门id
	private Integer deptId;
	// 状态 1.空闲 2预占 3占用
	private Integer status;
	// 资源id
	private Integer resId;
	// 备注
	private String note;
	// 使用人
	private Integer userId;
	// 部门
	private String depts;
	// 机房
	private String rooms;
	// 地理位置类型:1-建筑物；2-楼层；3-房间
	private Integer locationType;
	// 地理位置
	private String locations;
	// 网络类型：1-公安网；2-互联网
	private Integer netType;
	// 房间号
	private Integer officeID;
	// 网络设备
	private Integer deviceID;
	// vlanId
	private Integer vlanId;
	// vlan号
	private String vlanNo;
	//设备端口号
	private String devicePortNo;
	//墙壁端口号
	private String portNo;
	
	private String subNetMark;
	private String deptName;
	private String startIp;
	private String endIp;
	private Integer useNum;
	private String ids;
	
	private Integer subNetType;
	private String gateway;
	private String dnsAddress;
	private String subNetTypeName;
	private String userName;
	private String statusName;
	
	/**
	 * 建筑物、楼层、房间号
	 */
	private Integer building;
	private String buildingName;
	private Integer floor;
	private String floorName;
	private String roomNum;
	// 使用备注
	private String useRemark;
	
	public String getUseRemark() {
		return useRemark;
	}
	public void setUseRemark(String useRemark) {
		this.useRemark = useRemark;
	}
	
	public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}

	public Integer getOfficeID() {
		return officeID;
	}

	public void setOfficeID(Integer officeID) {
		this.officeID = officeID;
	}

	public Integer getBuilding() {
		return building;
	}

	public void setBuilding(Integer building) {
		this.building = building;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getSubNetId() {
		return subNetId;
	}

	public void setSubNetId(Integer subNetId) {
		this.subNetId = subNetId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getSubNetMark() {
		return subNetMark;
	}

	public void setSubNetMark(String subNetMark) {
		this.subNetMark = subNetMark;
	}

	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getSubNetType() {
		return subNetType;
	}

	public void setSubNetType(Integer subNetType) {
		this.subNetType = subNetType;
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

	public String getSubNetTypeName() {
		return subNetTypeName;
	}

	public void setSubNetTypeName(String subNetTypeName) {
		this.subNetTypeName = subNetTypeName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDepts() {
		return depts;
	}

	public void setDepts(String depts) {
		this.depts = depts;
	}

	public String getRooms() {
		return rooms;
	}

	public void setRooms(String rooms) {
		this.rooms = rooms;
	}

	public Integer getLocationType() {
		return locationType;
	}

	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
	}

	public String getLocations() {
		return locations;
	}

	public void setLocations(String locations) {
		this.locations = locations;
	}

	public Integer getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public String getPortNo() {
		return portNo;
	}

	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	public String getDevicePortNo() {
		return devicePortNo;
	}

	public void setDevicePortNo(String devicePortNo) {
		this.devicePortNo = devicePortNo;
	}

	public Integer getVlanId() {
		return vlanId;
	}

	public void setVlanId(Integer vlanId) {
		this.vlanId = vlanId;
	}

	public String getVlanNo() {
		return vlanNo;
	}

	public void setVlanNo(String vlanNo) {
		this.vlanNo = vlanNo;
	}
}
