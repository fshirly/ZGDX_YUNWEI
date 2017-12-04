package com.fable.insightview.monitor.database.entity;

public class MySQLKPINameDef {
	// Connection
	public final static String MaxConns = "MaxConns";// 表空间大小
	public final static String Conns = "Conns";// 表空间空闲大小
	public final static String ConnUsage = "ConnUsage";// 表空间使用率
	// QueryCache
	public final static String QCacheFree = "QCacheFree";// 物理读次数
	public final static String QCacheFBlocks = "QCacheFBlocks";// 物理写次数
	public final static String QCacheTBlocks = "QCacheTBlocks";// 块读取数
	public final static String QCacheSize = "QCacheSize";// 写入磁盘的块数
	public final static String QCacheUsage = "QCacheUsage";// 读消耗的时间
	public final static String QCacheFragmentation = "QCacheFragmentation";// 写消耗的时间
	public final static String QCacheHits = "QCacheHits";// 文件状态
	// TableCache
	public final static String TBCacheOpened = "TBCacheOpened";
	public final static String TBCacheOpen = "TBCacheOpen";// Extent初始大小
	public final static String TBCache = "TBCache";// Extent下一个值
	public final static String TBCacheHits = "TBCacheHits";// Extent最小值
	public final static String TBCacheUsage = "TBCacheUsage";// Extent最大值
	// TableLock
	public final static String TBLockImmediate = "TBLockImmediate";// SGA大小
	public final static String TBLockWaited = "TBLockWaited";// Pool池
	public final static String TBLockHits = "TBLockHits";// 数据缓冲区
	// TempTable
	public final static String TmpTables = "TmpTables";// 固定区大小
	public final static String TmpDiskTables = "TmpDiskTables";// 重做日志缓存
	public final static String TmpTableUsage = "TmpTableUsage";// 库缓存

}
