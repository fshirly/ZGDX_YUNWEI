package com.fable.insightview.monitor.database.entity;

public class OracleKPINameDef {
	//表空间
	public final static String TABLESPACESIZE = "TableSpaceSize";//表空间大小
	public final static String TABLESPACEFREE = "TableSpaceFree";//表空间空闲大小
	public final static String TABLESPACEUSAGE = "TableSpaceUsage";//表空间使用率
	public final static String TABLESPACESTATUS = "TableSpaceStatus";//表空间状态
	//数据文件
	public final static String DATAFILEPHYRDS = "DataFilePhyRds";//物理读次数
	public final static String DATAFILEPHYWRTS = "DataFilePhyWrts";//物理写次数
	public final static String DATAFILEPHYBLKRD = "DataFilePhyBlkRd";//块读取数
	public final static String DATAFILEPHYBLKWRT = "DataFilePhyBlkWrt";//写入磁盘的块数
	public final static String DATAFILERTIME = "DataFileRTime";//读消耗的时间
	public final static String DATAFILEWTIME = "DataFileWTime";//写消耗的时间
	public final static String DATAFILESTATUS = "DataFileStatus";//文件状态
	public final static String DATAFILECURRSIZE = "DataFileCurrSize";//文件当前大小
	public final static String DATAFILECRTSIZE = "DataFileCrtSize";//文件创建大小
	//回滚段
	public final static String ROLLBACKSEGSIZE = "RollBackSegSize";
	public final static String ROLLBACKSEGINIT = "RollBackSegInit";//Extent初始大小
	public final static String ROLLBACKSEGNEXT = "RollBackSegNext";//Extent下一个值
	public final static String ROLLBACKSEGMIN = "RollBackSegMin";//Extent最小值
	public final static String ROLLBACKSEGMAX = "RollBackSegMax";//Extent最大值
	public final static String ROLLBACKSEGPCT = "RollBackSegPct";//
	public final static String ROLLBACKSEGSTATUS = "RollBackSegStatus";//段状态
	public final static String ROLLBACKSEGHWMSIZE = "RollBackSegHwmSize";//HWMSize
	public final static String ROLLBACKSEGSHRINKS = "RollBackSegShrinks";//Shrinks
	public final static String ROLLBACKSEGWRAPS = "RollBackSegWraps";//Wraps
	public final static String ROLLBACKSEGEXTEND = "RollBackSegExtend";//
	//SGA
	public final static String SGASIZE = "SgaSize";//SGA大小
	public final static String SGAPOOL = "SgaPool";//Pool池
	public final static String SGABUFFER = "SgaBuffer";// 数据缓冲区
	public final static String SGAFIXED = "SgaFixed";//固定区大小 
	public final static String SGAREDO = "SgaRedo";//重做日志缓存
	public final static String SGALIBRARY = "SgaLibrary";// 库缓存
	public final static String SGADICTIONARY = "SgaDictionary";// 数据字典缓存
	public final static String SGASHARED = "SgaShared";//共享池大小
	public final static String SGAJAVA = "SgaJava";//Java池大小
	public final static String SGALARGE = "SgaLarge";// 大池大小
	public final static String SGASTREAMS = "SgaStreams";//流池大小
	public final static String SGAFREE = "SgaFree";// 
	
	
	public final static String DEVICE_UP = "1";// 设备状态  up
	public final static String DEVICE_DOWN = "2";// 设备状态 down
	public final static String IMGPATH="/style/images/levelIcon/";
}
