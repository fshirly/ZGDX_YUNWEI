package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfSybaseDatabaseBean {
	 private Integer id;
		private Integer moId;
		private Date collectTime;
		private long dBFileSize;//总空间
		private long usedSize;//已用空间
		private long freeSize;//空闲空间
		private double spaceUsage;//空间使用率        
		private long totalPage;//总页数
		private long usedPage;//已用页数
		private long freePage;//空闲页数
		private double usagePage;//页使用率
		private long logSpace;//日志总空间
		private long logUsedSpace;//日志已用空间
		private long logFreeSpace;//日志空闲空间
		private double logSpaceUsage;//日志空间使用率
		private long dataFileUsedSpace;//数据总空间
		private long dataFileFreeSpace;//数据空闲空间
		private double dataFileSpaceUsage;//数据空间使用率
		private long logPages;//日志总页
		private long logUsedPages;//日志已用页
		private long logFreePages;//日志空闲页
		private double logPageUsage;//日志页使用率
		private long dataFilePages;//数据总页
		private long dataFileUsedPages;//数据已用页
		private long dataFileFreePages;//数据空闲页
		private double dataFileUsagePages;//数据页使用率
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
		public long getdBFileSize() {
			return dBFileSize;
		}
		public void setdBFileSize(long dBFileSize) {
			this.dBFileSize = dBFileSize;
		}
		public long getUsedSize() {
			return usedSize;
		}
		public void setUsedSize(long usedSize) {
			this.usedSize = usedSize;
		}
		public long getFreeSize() {
			return freeSize;
		}
		public void setFreeSize(long freeSize) {
			this.freeSize = freeSize;
		}
		public double getSpaceUsage() {
			return spaceUsage;
		}
		public void setSpaceUsage(double spaceUsage) {
			this.spaceUsage = spaceUsage;
		}
		public long getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(long totalPage) {
			this.totalPage = totalPage;
		}
		public long getUsedPage() {
			return usedPage;
		}
		public void setUsedPage(long usedPage) {
			this.usedPage = usedPage;
		}
		public long getFreePage() {
			return freePage;
		}
		public void setFreePage(long freePage) {
			this.freePage = freePage;
		}
		public double getUsagePage() {
			return usagePage;
		}
		public void setUsagePage(double usagePage) {
			this.usagePage = usagePage;
		}
		public long getLogSpace() {
			return logSpace;
		}
		public void setLogSpace(long logSpace) {
			this.logSpace = logSpace;
		}
		public long getLogUsedSpace() {
			return logUsedSpace;
		}
		public void setLogUsedSpace(long logUsedSpace) {
			this.logUsedSpace = logUsedSpace;
		}
		public long getLogFreeSpace() {
			return logFreeSpace;
		}
		public void setLogFreeSpace(long logFreeSpace) {
			this.logFreeSpace = logFreeSpace;
		}
		public double getLogSpaceUsage() {
			return logSpaceUsage;
		}
		public void setLogSpaceUsage(double logSpaceUsage) {
			this.logSpaceUsage = logSpaceUsage;
		}
		public long getDataFileUsedSpace() {
			return dataFileUsedSpace;
		}
		public void setDataFileUsedSpace(long dataFileUsedSpace) {
			this.dataFileUsedSpace = dataFileUsedSpace;
		}
		public long getDataFileFreeSpace() {
			return dataFileFreeSpace;
		}
		public void setDataFileFreeSpace(long dataFileFreeSpace) {
			this.dataFileFreeSpace = dataFileFreeSpace;
		}
		public double getDataFileSpaceUsage() {
			return dataFileSpaceUsage;
		}
		public void setDataFileSpaceUsage(double dataFileSpaceUsage) {
			this.dataFileSpaceUsage = dataFileSpaceUsage;
		}
		public long getLogPages() {
			return logPages;
		}
		public void setLogPages(long logPages) {
			this.logPages = logPages;
		}
		public long getLogUsedPages() {
			return logUsedPages;
		}
		public void setLogUsedPages(long logUsedPages) {
			this.logUsedPages = logUsedPages;
		}
		public long getLogFreePages() {
			return logFreePages;
		}
		public void setLogFreePages(long logFreePages) {
			this.logFreePages = logFreePages;
		}
		public double getLogPageUsage() {
			return logPageUsage;
		}
		public void setLogPageUsage(double logPageUsage) {
			this.logPageUsage = logPageUsage;
		}
		public long getDataFilePages() {
			return dataFilePages;
		}
		public void setDataFilePages(long dataFilePages) {
			this.dataFilePages = dataFilePages;
		}
		public long getDataFileUsedPages() {
			return dataFileUsedPages;
		}
		public void setDataFileUsedPages(long dataFileUsedPages) {
			this.dataFileUsedPages = dataFileUsedPages;
		}
		public long getDataFileFreePages() {
			return dataFileFreePages;
		}
		public void setDataFileFreePages(long dataFileFreePages) {
			this.dataFileFreePages = dataFileFreePages;
		}
		public double getDataFileUsagePages() {
			return dataFileUsagePages;
		}
		public void setDataFileUsagePages(double dataFileUsagePages) {
			this.dataFileUsagePages = dataFileUsagePages;
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
	
		
		
}
