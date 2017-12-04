package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfSybaseDBDeviceBean {
	    private Integer id;
		private Integer moId;
		private Date collectTime;
		private long deviceSize;//总空间
		private long deviceUsedSize;//已用空间
		private long deviceFreeSize;//空闲空间
		private double deviceUsage;//空间使用率
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getMoId() {
			return moId;
		}
		public void setMoId(Integer moId) {
			this.moId = moId;
		}
		public Date getCollectTime() {
			return collectTime;
		}
		public void setCollectTime(Date collectTime) {
			this.collectTime = collectTime;
		}
		public long getDeviceSize() {
			return deviceSize;
		}
		public void setDeviceSize(long deviceSize) {
			this.deviceSize = deviceSize;
		}
		public long getDeviceUsedSize() {
			return deviceUsedSize;
		}
		public void setDeviceUsedSize(long deviceUsedSize) {
			this.deviceUsedSize = deviceUsedSize;
		}
		public long getDeviceFreeSize() {
			return deviceFreeSize;
		}
		public void setDeviceFreeSize(long deviceFreeSize) {
			this.deviceFreeSize = deviceFreeSize;
		}
		public double getDeviceUsage() {
			return deviceUsage;
		}
		public void setDeviceUsage(double deviceUsage) {
			this.deviceUsage = deviceUsage;
		}
    
}
