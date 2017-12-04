package com.fable.insightview.monitor.molist.entity;

import org.apache.ibatis.type.Alias;

import com.fable.insightview.platform.core.dao.idgenerator.NumberGenerator;

/**
 * 监测器与厂商设备型号关系
 * @author caoj
 */

@Alias("sysMOManufacturerResDefClass")
public class SysMOManufacturerResDefBean {
	@NumberGenerator(name = "MonitorMOManuResDefPK")
	private Integer id;
	private Integer resManufacturerId;  //厂商Id
	private Integer resCategoryId;      //设备型号id
	private Integer mid;                //监测器编号
	private String resManufacturerName;
	private String resCategoryName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResManufacturerId() {
		return resManufacturerId;
	}

	public void setResManufacturerId(Integer resManufacturerId) {
		this.resManufacturerId = resManufacturerId;
	}

	public Integer getResCategoryId() {
		return resCategoryId;
	}

	public void setResCategoryId(Integer resCategoryId) {
		this.resCategoryId = resCategoryId;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getResManufacturerName() {
		return resManufacturerName;
	}

	public void setResManufacturerName(String resManufacturerName) {
		this.resManufacturerName = resManufacturerName;
	}

	public String getResCategoryName() {
		return resCategoryName;
	}

	public void setResCategoryName(String resCategoryName) {
		this.resCategoryName = resCategoryName;
	}

}
