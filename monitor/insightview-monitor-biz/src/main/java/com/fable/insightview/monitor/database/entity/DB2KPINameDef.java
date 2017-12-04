package com.fable.insightview.monitor.database.entity;

public class DB2KPINameDef {
	//数据库
	public final static String SPILLEDUSAGE = "SpilledSorts";//排序溢出率
	public final static String LOGUSAGE = "LogUtil"; //日志利用率
	public final static String SHAREMEMUSAGE= "ShareUtil"; //共享排序内存利用率 
	public final static String WAITINGLOCKUSAGE = "AppsWaitingLocks"; //等待锁定的应用程序比率
	public final static String PKGCACHEHITS = "PkgCacheHits"; //程序包高速缓存命中率
	public final static String CATCACHEHITS = "CatCacheHits"; //目录高速缓存命中率
	public final static String DEADLOCKS = "DeadLocks"; //死锁数
	public final static String LOCKESCALS = "LockEscals"; //锁定升级次数
	public final static String SUCCSQLS = "SuccessQueries"; //成功执行的 SQL 语句数
	public final static String FAILEDSQLS = "FailedSqlStmts"; //执行失败的SQL语句数
	public final static String WORKUNITS = "WorkUnits"; //工作单元总数
	
	public final static String TBUsage = "TableSpaceUsage"; //表空间使用率
	
	public final static String TotalAgents = "AgentsRegistered"; //总代理数
	public final static String ActiveAgents = "ActiveAgents"; //活跃代理数
	public final static String IdleAgents = "IdleAgents"; //空闲代理数
	//缓冲池
	public final static String BUFFERPOOLHITS = "BPHits"; //缓冲池命中率
	public final static String INDEXHITS = "IndexHits"; //索引缓冲池命中率
	public final static String DATAPAGEHITS = "DataPageHits"; //数据页命中率
	public final static String DIRECTREADS = "DirectReads"; //直接读取数
	public final static String DIRECTWRITES = "DirectWrites"; //直接写入数
	public final static String DIRECTREADTIME = "DirectReadTime"; //直接读取时间
	public final static String DIRECTWRITETIME = "DirectWriteTime"; //直接写入时间
	
}
