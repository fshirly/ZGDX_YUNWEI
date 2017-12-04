package com.fable.insightview.monitor.database.entity;

import java.util.Date;

public class PerfMSSQLServerBean {
	 private Integer id;
	 private Integer moId;
	 private Date collectTime;
	 private double buffCacheHits;//缓存命中率
	 private int totalPages;//缓存总页数
	 private int freePages;//缓存空闲页数
	 private int databasePages;//缓存中有数据库内容的页数
	 private int checkpointPages;//每秒刷新到磁盘的页数
	 private int freeListStalls;//每秒必须等待可用页的请求次数
	 private int lazyWrites;//每秒延迟写次数
	 private int pageReads;//每秒物理数据库页读次数
	 private int pageWrites;//每秒物理数据库页写次数
	 private int pageLookups;//每秒查找页的请求数
	 private double totalServMemory;//总内存
	 private double connectionMemory;//用来维护连接的内存
	 private double lockMemory;//用于锁的内存
	 private double optimizerMemory;//用于查询优化的内存
	 private double sqlCacheMemory;//用于SQL高速缓存的内存
	 private double grantedMemory;//执行排序等操作的内存
	 private int logins;//每秒登录数
	 private int logouts;//每秒注销数
	 private int userConnections;//用户连接数
	 private int cacheObjects;//高速缓存对象数
	 private int cachePages;//高速缓存对象使用的页数
	 private int lockRequests;//每秒锁请求数
	 private int lockTimeouts;//每秒超时的锁请求数
	 private int lockWaits;//每秒要求调用者等待的锁请求数
	 private int deadLocks;//每秒导致死锁的锁请求数
	 private long avgWaitTime;//锁请求平均等待时间
	 private int batchRequests;//每秒收到的批SQL命令数
	 private int sqlCompilations;//每秒SQL编译数
	 private int sqlReCompilations;//每秒SQL重编译数
	 private int autoParamAttempts;//每秒的自动参数化尝试数
	 private int failedAutoParams;//每秒自动参数化尝试失败次数
	 private int latchWaits;//每秒未能立即授予的闩锁请求数
	 private long avgLatchWaitTime;//闩锁请求平均等待时间
	 private long fullScans;//每秒完全扫描数
	 private long rangeScans;//每秒通过索引进行的限定范围的扫描数
     private long tableLockEscalations;//每秒表上的锁升级次数
     private long worktablesCreated;//每秒创建的工作表数
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
	public double getBuffCacheHits() {
		return buffCacheHits;
	}
	public void setBuffCacheHits(double buffCacheHits) {
		this.buffCacheHits = buffCacheHits;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getFreePages() {
		return freePages;
	}
	public void setFreePages(int freePages) {
		this.freePages = freePages;
	}
	public int getDatabasePages() {
		return databasePages;
	}
	public void setDatabasePages(int databasePages) {
		this.databasePages = databasePages;
	}
	public int getCheckpointPages() {
		return checkpointPages;
	}
	public void setCheckpointPages(int checkpointPages) {
		this.checkpointPages = checkpointPages;
	}
	public int getFreeListStalls() {
		return freeListStalls;
	}
	public void setFreeListStalls(int freeListStalls) {
		this.freeListStalls = freeListStalls;
	}
	public int getLazyWrites() {
		return lazyWrites;
	}
	public void setLazyWrites(int lazyWrites) {
		this.lazyWrites = lazyWrites;
	}
	public int getPageReads() {
		return pageReads;
	}
	public void setPageReads(int pageReads) {
		this.pageReads = pageReads;
	}
	public int getPageWrites() {
		return pageWrites;
	}
	public void setPageWrites(int pageWrites) {
		this.pageWrites = pageWrites;
	}
	public int getPageLookups() {
		return pageLookups;
	}
	public void setPageLookups(int pageLookups) {
		this.pageLookups = pageLookups;
	}
	public double getTotalServMemory() {
		return totalServMemory;
	}
	public void setTotalServMemory(double totalServMemory) {
		this.totalServMemory = totalServMemory;
	}
	public double getConnectionMemory() {
		return connectionMemory;
	}
	public void setConnectionMemory(double connectionMemory) {
		this.connectionMemory = connectionMemory;
	}
	public double getLockMemory() {
		return lockMemory;
	}
	public void setLockMemory(double lockMemory) {
		this.lockMemory = lockMemory;
	}
	public double getOptimizerMemory() {
		return optimizerMemory;
	}
	public void setOptimizerMemory(double optimizerMemory) {
		this.optimizerMemory = optimizerMemory;
	}
	public double getSqlCacheMemory() {
		return sqlCacheMemory;
	}
	public void setSqlCacheMemory(double sqlCacheMemory) {
		this.sqlCacheMemory = sqlCacheMemory;
	}
	public double getGrantedMemory() {
		return grantedMemory;
	}
	public void setGrantedMemory(double grantedMemory) {
		this.grantedMemory = grantedMemory;
	}
	public int getLogins() {
		return logins;
	}
	public void setLogins(int logins) {
		this.logins = logins;
	}
	public int getLogouts() {
		return logouts;
	}
	public void setLogouts(int logouts) {
		this.logouts = logouts;
	}
	public int getUserConnections() {
		return userConnections;
	}
	public void setUserConnections(int userConnections) {
		this.userConnections = userConnections;
	}
	public int getCacheObjects() {
		return cacheObjects;
	}
	public void setCacheObjects(int cacheObjects) {
		this.cacheObjects = cacheObjects;
	}
	public int getCachePages() {
		return cachePages;
	}
	public void setCachePages(int cachePages) {
		this.cachePages = cachePages;
	}
	public int getLockRequests() {
		return lockRequests;
	}
	public void setLockRequests(int lockRequests) {
		this.lockRequests = lockRequests;
	}
	public int getLockTimeouts() {
		return lockTimeouts;
	}
	public void setLockTimeouts(int lockTimeouts) {
		this.lockTimeouts = lockTimeouts;
	}
	public int getLockWaits() {
		return lockWaits;
	}
	public void setLockWaits(int lockWaits) {
		this.lockWaits = lockWaits;
	}
	public int getDeadLocks() {
		return deadLocks;
	}
	public void setDeadLocks(int deadLocks) {
		this.deadLocks = deadLocks;
	}
	public long getAvgWaitTime() {
		return avgWaitTime;
	}
	public void setAvgWaitTime(long avgWaitTime) {
		this.avgWaitTime = avgWaitTime;
	}
	public int getBatchRequests() {
		return batchRequests;
	}
	public void setBatchRequests(int batchRequests) {
		this.batchRequests = batchRequests;
	}
	public int getSqlCompilations() {
		return sqlCompilations;
	}
	public void setSqlCompilations(int sqlCompilations) {
		this.sqlCompilations = sqlCompilations;
	}
	public int getSqlReCompilations() {
		return sqlReCompilations;
	}
	public void setSqlReCompilations(int sqlReCompilations) {
		this.sqlReCompilations = sqlReCompilations;
	}
	public int getAutoParamAttempts() {
		return autoParamAttempts;
	}
	public void setAutoParamAttempts(int autoParamAttempts) {
		this.autoParamAttempts = autoParamAttempts;
	}
	public int getFailedAutoParams() {
		return failedAutoParams;
	}
	public void setFailedAutoParams(int failedAutoParams) {
		this.failedAutoParams = failedAutoParams;
	}
	public int getLatchWaits() {
		return latchWaits;
	}
	public void setLatchWaits(int latchWaits) {
		this.latchWaits = latchWaits;
	}
	public long getAvgLatchWaitTime() {
		return avgLatchWaitTime;
	}
	public void setAvgLatchWaitTime(long avgLatchWaitTime) {
		this.avgLatchWaitTime = avgLatchWaitTime;
	}
	public long getFullScans() {
		return fullScans;
	}
	public void setFullScans(long fullScans) {
		this.fullScans = fullScans;
	}
	public long getRangeScans() {
		return rangeScans;
	}
	public void setRangeScans(long rangeScans) {
		this.rangeScans = rangeScans;
	}
	public long getTableLockEscalations() {
		return tableLockEscalations;
	}
	public void setTableLockEscalations(long tableLockEscalations) {
		this.tableLockEscalations = tableLockEscalations;
	}
	public long getWorktablesCreated() {
		return worktablesCreated;
	}
	public void setWorktablesCreated(long worktablesCreated) {
		this.worktablesCreated = worktablesCreated;
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
