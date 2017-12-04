package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfSybaseServerBean {
	    private Integer id;
		private Integer moId;
		private Date collectTime;
		private long  externalTransactionNum; //外部事务数
		private long  minuteExternalTransactionNum;//每分钟外部事务数
		private long  localTransactionNum; //本地事务数
		private long  transactionNum; //事务数
		private long  minuteTransactionNum;//每分钟事务数
		private long  activeLockNum;//活跃锁个数
		private long  activePageLockNum;  // 活跃页级锁个数
		private long  activeTabLockNum;  //活跃表级锁个数   
		private long  activeUser;     //活跃用户数     
		private long  txLog;   //TxLog数             
		private long  packReceive;//接收包数          
		private long  packSent;  //发送包数           
		private long  packetErrors;  //错误包数       
		private long  totalDiskRead; //磁盘读次数      
		private long  totalDiskWrite; //磁盘写次数      
		private long  totalDiskErrors;//磁盘读写错误次数      
		private String  cpuBusy;    //cpu处理耗时          
		private String  cpuIdle;   // cpu空闲时间          
		private long  ioBusy;    // IO处理耗时          
		private long  userConnections; //用户连接数     
		private long  totalSysProcesses;   // 进程总数
		private long  blockingSysProcesses;//阻塞进程数
		private long  activeProcesses;  // 活跃进程数   
		private long  sysProcesses; // 系统进程数       
		private long  idleSysProcesses; //空闲进程数
		private String formatTime;
	    private long perfValue;
	     
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
		public long getExternalTransactionNum() {
			return externalTransactionNum;
		}
		public void setExternalTransactionNum(long externalTransactionNum) {
			this.externalTransactionNum = externalTransactionNum;
		}
		public long getMinuteExternalTransactionNum() {
			return minuteExternalTransactionNum;
		}
		public void setMinuteExternalTransactionNum(long minuteExternalTransactionNum) {
			this.minuteExternalTransactionNum = minuteExternalTransactionNum;
		}

		public long getLocalTransactionNum() {
			return localTransactionNum;
		}
		public void setLocalTransactionNum(long localTransactionNum) {
			this.localTransactionNum = localTransactionNum;
		}
		public long getMinuteTransactionNum() {
			return minuteTransactionNum;
		}
		public void setMinuteTransactionNum(long minuteTransactionNum) {
			this.minuteTransactionNum = minuteTransactionNum;
		}
		public long getActiveLockNum() {
			return activeLockNum;
		}
		public void setActiveLockNum(long activeLockNum) {
			this.activeLockNum = activeLockNum;
		}
		public long getActivePageLockNum() {
			return activePageLockNum;
		}
		public void setActivePageLockNum(long activePageLockNum) {
			this.activePageLockNum = activePageLockNum;
		}
		public long getActiveTabLockNum() {
			return activeTabLockNum;
		}
		public void setActiveTabLockNum(long activeTabLockNum) {
			this.activeTabLockNum = activeTabLockNum;
		}
		public long getActiveUser() {
			return activeUser;
		}
		public void setActiveUser(long activeUser) {
			this.activeUser = activeUser;
		}
		public long getTxLog() {
			return txLog;
		}
		public void setTxLog(long txLog) {
			this.txLog = txLog;
		}
		public long getPackReceive() {
			return packReceive;
		}
		public void setPackReceive(long packReceive) {
			this.packReceive = packReceive;
		}
		public long getPackSent() {
			return packSent;
		}
		public void setPackSent(long packSent) {
			this.packSent = packSent;
		}
		public long getPacketErrors() {
			return packetErrors;
		}
		public void setPacketErrors(long packetErrors) {
			this.packetErrors = packetErrors;
		}
		public long getTotalDiskRead() {
			return totalDiskRead;
		}
		public void setTotalDiskRead(long totalDiskRead) {
			this.totalDiskRead = totalDiskRead;
		}
		public long getTotalDiskWrite() {
			return totalDiskWrite;
		}
		public void setTotalDiskWrite(long totalDiskWrite) {
			this.totalDiskWrite = totalDiskWrite;
		}
		public long getTotalDiskErrors() {
			return totalDiskErrors;
		}
		public void setTotalDiskErrors(long totalDiskErrors) {
			this.totalDiskErrors = totalDiskErrors;
		}
		public String getCpuBusy() {
			return cpuBusy;
		}
		public void setCpuBusy(String cpuBusy) {
			this.cpuBusy = cpuBusy;
		}
		public String getCpuIdle() {
			return cpuIdle;
		}
		public void setCpuIdle(String cpuIdle) {
			this.cpuIdle = cpuIdle;
		}
		public long getIoBusy() {
			return ioBusy;
		}
		public void setIoBusy(long ioBusy) {
			this.ioBusy = ioBusy;
		}
		public long getUserConnections() {
			return userConnections;
		}
		public void setUserConnections(long userConnections) {
			this.userConnections = userConnections;
		}
		public long getTotalSysProcesses() {
			return totalSysProcesses;
		}
		public void setTotalSysProcesses(long totalSysProcesses) {
			this.totalSysProcesses = totalSysProcesses;
		}
		public long getBlockingSysProcesses() {
			return blockingSysProcesses;
		}
		public void setBlockingSysProcesses(long blockingSysProcesses) {
			this.blockingSysProcesses = blockingSysProcesses;
		}
		public long getActiveProcesses() {
			return activeProcesses;
		}
		public void setActiveProcesses(long activeProcesses) {
			this.activeProcesses = activeProcesses;
		}
		public long getSysProcesses() {
			return sysProcesses;
		}
		public void setSysProcesses(long sysProcesses) {
			this.sysProcesses = sysProcesses;
		}
		public long getIdleSysProcesses() {
			return idleSysProcesses;
		}
		public void setIdleSysProcesses(long idleSysProcesses) {
			this.idleSysProcesses = idleSysProcesses;
		}
		public String getFormatTime() {
			return formatTime;
		}
		public void setFormatTime(String formatTime) {
			this.formatTime = formatTime;
		}
		public long getPerfValue() {
			return perfValue;
		}
		public void setPerfValue(long perfValue) {
			this.perfValue = perfValue;
		}
		public long getTransactionNum() {
			return transactionNum;
		}
		public void setTransactionNum(long transactionNum) {
			this.transactionNum = transactionNum;
		}
		
		
}
