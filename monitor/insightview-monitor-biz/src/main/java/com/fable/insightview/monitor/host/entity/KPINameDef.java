package com.fable.insightview.monitor.host.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class KPINameDef {
	public final static String CPUSAGE = "CPUsage";// 平均CPU使用率
	public final static String PERUSAGE = "PerUsage";// CPU使用率
	public final static String PHYMEMUSAGE = "PhyMemUsage";// 物理内存使用率
	public final static String PHYMEMSIZE = "PhyMemSize";// 物理内存大小
	public final static String PHYMEMFREE = "PhyMemFree";// 物理内存空闲大小
	public final static String VIRMEMUSAGE = "VirMemUsage";// 虚拟内存使用率
	public final static String VIRMEMSIZE = "VirMemSize";// 虚拟内存大小
	public final static String VIRMEMFREE = "VirMemFree";// 虚拟内存空闲大小
	public final static String DISKUSAGE = "DiskUsage";// 硬盘使用率
	public final static String FLOWS = "Flows";// 总流量
	public final static String INFLOWS = "InFlows";// 流入流量
	public final static String OUTFLOWS = "OutFlows";// 流出流量
	public final static String INUSAGE = "InUsage";// 带宽流入使用率
	public final static String OUTUSAGE = "OutUsage";// 带宽流出使用率
	public final static String RATE = "Rate";// 带宽速率
	public final static String IFUSAGE = "IfUsage";// 总带宽使用率
	public final static String INPACKETS = "InPackets";// 流入单播包
	public final static String OUTPACKETS = "OutPackets";// 流出单播包
	public final static String INERRORS = "InErrors";// 流入错包
	public final static String OUTERRORS = "OutErrors";// 流出错包
	public final static String INDISCARDS = "InDiscards";// 流入丢包
	public final static String OUTDISCARDS = "OutDiscards";// 流出丢包
	public final static String INNPACKETS = "InNPackets";// 流入非单播包
	public final static String OUTNPACKETS = "OutNPackets";// 接口非单播包
	public final static String IFOPERSTATUS="ifOperStatus";//接口操作状态
	public final static String NETMEMUSAGE="NetMemUsage";//路由器多个内存 取平均
	public final static String IFSPEED = "IfSpeed";// 带宽速率
	

	public final static String AVAILABILITY = "DeviceAvailability";// 可用性
	public final static String DEVICELOSS = "DeviceLoss";// 丢失
	public final static String DEVICERESPONSE = "DeviceResponse";// 响应
	public final static String DISKFREE = "DiskFree";// 硬盘空闲大小
	public final static String DISKSIZE = "DiskSize";// 硬盘可用存储空间
	public final static String DISKUSED = "DiskUsed";// 硬盘已用大小
	// 宿主机
	public final static String CPUSED = "CPUsed";// 总使用时间
	public final static String CPUIDLE = "CPUIdle";// 总空闲时间
	public final static String DISKRATE = "DiskRate";
	// 虚拟机
	public final static String CPUTIL = "CPUtil";// 总使用时间
	public final static String CPUREADY = "CPUReady";// 准备时间
	public final static String CPUWAIT = "CPUWait";// 等待时间
	public final static String PERUTIL ="PerUtil";//CPU使用量
	// 数据存储
	public final static String DATASTOREFREE = "DatastoreFree";// 空闲容量
	public final static String DATASTOREUSAGE = "DatastoreUsage";// 数据存储使用率(%)
	public final static String DATASTORERSPEED = "DatastoreRSpeed";// 读速度
	public final static String DATASTOREWSPEED = "DatastoreWSpeed";// 写速度
	public final static String DATASTORERREQUEST = "DatastoreRRequest";// 读请求
	public final static String DATASTOREWREQUEST = "DatastoreWRequest";// 写请求
	public final static String DATASTORENLATENCY = "DatastoreNLatency";// 规范延时(秒)
	public final static String DATASTOREIOPS = "DatastoreIOPS";// IO操作数据存储总数
	public final static String DATASTORERLATENCY = "DatastoreRLatency";// 读延时(秒)
	public final static String DATASTOREWLATENCY = "DatastoreWLatency";// 写延时(秒)
	// 硬盘
	public final static String DISKRSPEED = "DiskRSpeed";// 读盘速度
	public final static String DISKWSPEED = "DiskWSpeed";// 写盘速度
	public final static String DISKRREQUEST = "DiskRRequest";// 读盘请求
	public final static String DISKWREQUEST = "DiskWRequest";// 写盘请求
	public final static String DISKRESET = "DiskReset";// 磁盘总线重置
	public final static String DISKABORT = "DiskAbort";// 终止磁盘命令
	public final static String DISKRLATENCY = "DiskRLatency";// 读盘延时(秒)
	public final static String DISKWLATENCY = "DiskWLatency";// 写盘延时(秒)
	// 虚拟机 内存
	public final static String VIRMEMACTIVE = "VirMemActive";// 活动内存
	public final static String VIRMEMOVERHEAD = "VirMemOverhead";// 系统开销内存
	public final static String VIRMEMSHARED = "VirMemShared";// 共享内存
	public final static String VIRMEMCSHARED = "VirMemCShared";// 共享的通用内存
	public final static String VIRMEMSWAPIN = "VirMemSwapIn";// 写入交换内存
	public final static String VIRMEMSWAPOUT = "VirMemSwapOut";// 读出交换内存
	public final static String VIRMEMSWAPUSED = "VirMemSwapUsed";// 已用交换内存

	// 内存
	public final static String PHYMEMACTIVE = "PhyMemActive";// 活动内存
	public final static String PHYMEMOVERHEAD = "PhyMemOverhead";// 系统开销内存
	public final static String PHYMEMSHARED = "PhyMemShared";// 共享内存
	public final static String PHYMEMCSHARED = "PhyMemCShared";// 共享的通用内存
	public final static String PHYMEMSWAPIN = "PhyMemSwapIn";// 写入交换内存
	public final static String PHYMEMSWAPOUT = "PhyMemSwapOut";// 读出交换内存
	public final static String PHYMEMSWAPUSED = "PhyMemSwapUsed";// 已用交换内存
	public final static String PHYMEMCONSUMED = "PhyMemConsumed";// 消耗内存

	public final static String CONNECTED = "connected";// 虚拟机状态 已连接
	public final static String DISCONNECTED = "disconnected";// 虚拟机状态 已断开
	public final static String INACCESSIBLE = "inaccessible";// 不可取的
	public final static String INVALID = "invalid";// 虚拟机状态 非法的
	public final static String ORPHANED = "orphaned";// 虚拟机状态 独立的

	public final static String POWEREDON = "poweredOn";// 打开
	public final static String POWEREDOFF = "poweredOff";// 关闭
	public final static String SUSPENDED = "suspended";// 挂起

	public final static String NOTRESPONDING = "3";

	public final static String DEVICE_UP = "1";// 设备状态 up
	public final static String DEVICE_DOWN = "2";// 设备状态 down

	public final static int up = 1;// up
	public final static int down = 2;// down
	
	public final static  double upd= 1;// up
	public final static double downd = 2;// down
	
	public final static long IFOPERSTATUS_UP=1;
	
	public final static int JOBMYSQLAVAILABLE = 26;
	public final static int JOBORACLEAVAILABLE = 32;
	public final static int JOBDB2AVAILABLE = 72;
	public final static int JOBMSSQLSERVERAVAILABLE = 90;
	public final static int JOBSYBASESERVERAVAILABLE = 94;
	public final static int JOBURLPOLL = 51;//中间件可用性周期
	public final static int JOBPINGPOLL = 10;//设备可用性周期
	public final static int JOBSITEDNS=83;
	public final static int JOBSITEFTP=84;
	public final static int JOBSITEHTTP=85;
	public final static int JOBSITETCP=87;
	public final static int JobConditionsEmerson = 95;//空调可用性周期
	public final static int JobConditionsUPS = 96;//UPS可用性周期
	/**
	 * 设置查询时间(公共调用方法)
	 * @return
	 */
	public static Map queryBetweenTime() {
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";
		d.add(Calendar.HOUR, -1);
		
		timeBegin = f.format(d.getTime());
		System.out.println("timeBegin=" + timeBegin + "\ntimeEnd=" + timeEnd);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
	/**
	 * 设置查询时间(公共调用方法)
	 * @return
	 */
	public static Map queryBetweenDay() {
		Map<String, Object> params = new HashMap<String, Object>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar d = Calendar.getInstance();
		String timeEnd = f.format(d.getTime());
		String timeBegin = "";
		d.add(Calendar.DATE, -1);
		
		timeBegin = f.format(d.getTime());
		System.out.println("timeBegin=" + timeBegin + "\ntimeEnd=" + timeEnd);
		params.put("timeBegin", timeBegin);
		params.put("timeEnd", timeEnd);
		return params;
	}
	
}
