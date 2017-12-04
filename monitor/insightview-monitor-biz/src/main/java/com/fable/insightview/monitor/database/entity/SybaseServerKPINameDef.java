package com.fable.insightview.monitor.database.entity;

public class SybaseServerKPINameDef {
	public static final String EXTERNALTRANSACTIONNUM = "externalTransactionNum"; // 外部事务数
	public static final String MINUTEEXTERNALTRANSACTIONNUM = "minuteExternalTransactionNum";// 每分钟外部事务数
	public static final String LOCALTRANSACTIONNUM = "localTransactionNum"; // 事务数
	public static final String MINUTETRANSACTIONNUM = "minuteTransactionNum";// 每分钟事务数
	public static final String ACTIVELOCKNUM = "activeLockNum";// 活跃锁个数
	public static final String ACTIVEPAGELOCKNUM = "activePageLockNum"; // 活跃页级锁个数
	public static final String ACTIVETABLOCKNUM = "activeTabLockNum"; // 活跃表级锁个数
	public static final String ACTIVEUSER = "activeUser"; // 活跃用户数
	public static final String TXLOG = "txLog"; // TxLog数
	public static final String PACKRECEIVE = "packReceive";// 接收包数
	public static final String PACKSENT = "packSent"; // 发送包数
	public static final String PACKETERRORS = "packetErrors"; // 错误包数
	public static final String TOTALDISKREAD = "totalDiskRead"; // 磁盘读次数
	public static final String TOTALDISKWRITE = "totalDiskWrite"; // 磁盘写次数
	public static final String TOTALDISKERRORS = "totalDiskErrors";// 磁盘读写错误次数
	public static final String CPUBUSY = "cpuBusy"; // cpu处理耗时
	public static final String CPUIDLESERV = "CpuIdleServ"; // cpu空闲时间
	public static final String IOBUSY = "ioBusy"; // IO处理耗时
	public static final String USERCONNECTION = "UserConnection"; // 用户连接数
	public static final String TOTALSYSPROCESSES = "totalSysProcesses"; // 进程总数
	public static final String BLOCKINGSYSPROCESSES = "blockingSysProcesses";// 阻塞进程数
	public static final String ACTIVEPROCESSES = "activeProcesses"; // 活跃进程数
	public static final String SYSPROCESSES = "sysProcesses"; // 系统进程数
	public static final String IDLESYSPROCESSES = "idleSysProcesses"; // 空闲进程数

	public static final String DBFILESIZE = "dBFileSize";
	public static final String USEDSIZE = "usedSize";
	public static final String FREESIZE = "freeSize";
	public static final String SPACEUSAGE = "spaceUsage";
	public static final String TOTALPAGE = "totalPage";
	public static final String USEDPAGE = "usedPage";
	public static final String FREEPAGE = "freePage";
	public static final String USAGEPAGE = "usagePage";
	public static final String LOGSPACE = "logSpace";
	public static final String LOGUSEDSPACE = "logUsedSpace";
	public static final String LOGFREESPACE = "logFreeSpace";
	public static final String LOGSPACEUSAGE = "logSpaceUsage";
	public static final String DATAFILESPACE = "DataFileSpace";
	public static final String DATAFILEUSEDSPACE = "dataFileUsedSpace";
	public static final String DATAFILEFREESPACE = "dataFileFreeSpace";
	public static final String DATAFILESPACEUSAGE = "dataFileSpaceUsage";
	public static final String LOGPAGES = "logPages";
	public static final String LOGUSEDPAGES = "logUsedPages";
	public static final String LOGFREEPAGES = "logFreePages";
	public static final String LOGPAGEUSAGE = "logPageUsage";
	public static final String DATAFILEPAGES = "dataFilePages";
	public static final String DATAFILEUSEDPAGES = "dataFileUsedPages";
	public static final String DATAFILEFREEPAGES = "dataFileFreePages";
	public static final String DATAFILEUSAGEPAGES = "dataFileUsagePages";
}
