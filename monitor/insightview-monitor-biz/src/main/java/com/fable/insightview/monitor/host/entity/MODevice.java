package com.fable.insightview.monitor.host.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("modeviceInfo")
public class MODevice {
	private Integer moid;

	private String moname;

	private String moalias;

	private String operstatus;

	private String adminstatus;

	private Integer alarmlevel;

	private Integer domainid;

	private Date createtime;

	private Date lastupdatetime;

	private Integer createby;

	private Integer updateby;

	private String deviceip;

	private Integer necollectorid;

	private Integer nemanufacturerid;

	private Integer necategoryid;

	private String neversion;

	private String os;

	private String osversion;

	private Integer snmpversion;

	private Integer ismanage;

	private Integer taskid;

	private Integer moclassid;
	
	private String classIcon;

	private Integer devicemoid;
	
	private String alarmLevelName;

	private double cpusage;// 主机/ 路由器 /交换机 CPU使用率

	private String perusage;

	private Float usagecore;// 虚拟机CPU使用率

	private Float utilizationcore;// 宿主机CPU使用率

	private String restypename;

	private Date collecttime;

	private String collTime;

	private String memoryusage;// 主机/ 路由器 /交换机 内存使用率
	
	private String perMemoryUsage;

	private Float memusage;// 虚拟机/宿主机内存使用率

	private Integer virmemoryusage;// 主机虚拟内存使用率

	private String diskusage;// 主机硬盘使用率

	private String rawdescr;// 硬盘 分区名称

	private String ifname;// 接口名称
	
	private String ifMoName;// 接口表中的moname名称

	private String inflows;// 接口 流入量

	private String outflows;// 接口流出量
	
	private String flows;//接口总流量

	private double inerrors;// 流入错包

	private double outerrors;// 流出错包

	private double indiscards;// 流入丢包

	private double outdiscards;// 流出丢包

	private long inpackets;// 流入单播包

	private long outpackets;// 流出单播包

	private double discards;// 总丢包

	private double errors;// 总错包
	
	private String ifUsage;//总带宽使用率
	
	private int isCollect; //界面上配置的是否采集
	
	private String rate;//接口速率
	
	private long innpackets;// 流入非单播包

	private long outnpackets;// 流出非单播包

	private String inusage;// 接口带宽流入使用率

	private String outusage;// 接口带宽流出使用率
	// 主机详情
	private String rescategoryname;// 主机型号

	private int cpucount;// 主机个数

	private String hmemorysize;// 内存大小

	private String hmVirMemorysize;// 虚拟内存大小

	private String sumdisksize;// 硬盘大小

	private String disksize;// 详情列表 硬盘容量

	private String diskfree;// 详情列表 硬盘空闲容量
	
	private String diskused;//详情列表 磁盘已用大小

	private double ifcount;// 接口个数

	private long fullsize;//

	private long freesize;// 空闲大小

	// 虚拟机详情
	private String cpuallocated;// CPU频率

	private int cpunumber;// CPU个数

	private String memory;// 虚拟机内存

	private String memoryoverhead;// 虚拟内存使用量

	private String connectstatus;// 虚拟机状态

	// 接口详情
	private String ifdescr;// 接口描述

	private String ifalias;// 接口别名

	private String instance;// 接口号

	private String iftype;// 接口类型

	private String ifadminstatus;// 管理状态

	private String ifoperstatus;// 操作状态
	// 设备详情
	private String resmanufacturername;// 厂商名称
	// 宿主机详情
	private String cpucores;// CPU频率

	private int phyprocessors;// 物理CPU数

	private int logicalprocessors;// 逻辑CPU数

	private String processortype;// CPU型号

	private double cpuusage;// CPU使用率

	private String connectionstatus;// 连接状态

	private int nics;// 网络适配器数

	private int hbas;// 主机总线适配器数
	// 设备快照

	private int alarmcount;// 告警数量

	private int devicealarmcount;// 有问题的设备数量

	private int devicecount;// 设备数量
	
	private int countdevice;// 每个设备数量
	
	private int sourceMOClassID;
	// 主机视图列表
	private String cpuvalue;// cpu值

	private String memoryvalue;// 内存值

	private String devicestatus;// 设备状态 1-up 2-down

	private String levelicon;

	private String ifspeed;// 接口速率

	private int ifmtu;

	private String phymemsize;// 内存大小

	private String phymemfree;// 内存空闲大小

	private String phymemusage;// 内存使用率

	private String phymemactive;// 活动内存

	private String phymemoverhead;// 系统开销内存

	private String phymemshared;// 共享内存
	// 虚拟机

	private String virmemactive;// 活动内存

	private String virmemoverhead;// 系统开销内存

	private String virmemshared;// 共享内存

	private String virmemfree;// 虚拟内存空闲大小

	private String virmemsize;// 虚拟内存大小

	private String virmemusage;// 虚拟内存使用率

	private String cpuidle;// 总空闲时间

	private String cpused;// 总使用时间

	private String diskrate;// 宿主机 使用率

	private String datastoreusage;// 数据存储使用率

	private String datastorefree;// 空闲容量

	private String datastorerspeed;// 读速度

	private String datastorewspeed;// 写速度

	private long datastorerrequest;// 读请求

	private long datastorewrequest;// 写请求

	private String diskrspeed;// 硬盘读速度

	private String diskwspeed;// 硬盘写度

	private long diskrrequest;// 硬盘读请求

	private long diskwrequest;// 硬盘写请求
	// 虚拟机 CPU
	private String cputil;// 总使用量

	private String CPUReady;// 虚拟机CPU准备时间

	private String CPUWait;// 虚拟机CPU等待时间
	
	private String perUtil;//cpu使用量

	private long inflowloss;

	private long outflowloss;

	private long inflowerrors;

	private long outflowerrors;

	private String kpiname;

	private Long perfvalue;
	// zxh 代码
	private Float writespeed;

	private Float readspeed;

	private Float networkusage;

	private Float receivedspeed;

	private Float transmittedspeed;

	private Float iousage;

	private int ifMOID;

	private int ifDeviceMOID;

	private String ins;// 流入标识

	private String outs;// 流出标识

	private String classlable;// 设备类型

	private int count;

	private int flag;

	private String memorysize;
	// ********************zxl 曲线图
	private long inflowpkts;// 流入单播包

	private long outflowpkts;// 流出单播包

	private long inflownpkts;// 流入非单播包

	private long outflownpkts;// 流出非单播包

	private long used;// 总使用时间

	private long ready;// 准备时间

	private long wait;// 等待时间

	private long balloon;// 内存控制

	private long active;// 活动内存

	private long overhead;// 系统内存开销

	private long shared;// 共享内存

	private long swapped;// 交换内存

	private long consumed;// 消耗内存

	private Float readrequests;

	private Float writerequests;

	private Float readlatency;

	private Float writelatency;

	private Float busresets;

	private Float packetsreceived;

	private Float packetstransmitted;

	private long Idle;

	private long sharedcommon;

	private long swapin;

	private long swapout;

	private long swapused;

	private long normalizedlatency;

	private long iops;

	private Float commandaborts;
	
	private double deviceavailability;
	
	private long ifOperStatus;//接口操作状态
	
	private double viewValue;
	
	private String descStr;
	
	private String phyDeviceIP;//宿主机IP
    private Date updateAlarmTime;
	private String durationTime;//持续时间
	private Integer doIntervals; 
	private Integer defDoIntervals;
	private String  perValue;
	
	private Integer resId;
	
	private Integer blackPortID;//设备黑白名单接口/端口列表主键
	private Integer blackID;//设备黑白名单主键
	private String blackOprateStatus;//设备黑白名单接口/端口启用黑白名单状态 '1=启用 0=不启用'
	private String type;//设备黑白名单接口/端口名单种类 '1=黑名单  2=白名单'
	private Integer port;//设备黑白名单端口号
	private String portService;//端口对应服务，白名单需要
	private String portType;//TCP/UDP
	private String gatewayIP;//对应网关ip地址
	private String ifTypeName;//接口类型名称
	private String memType;
	
	public String getDescStr() {
		return descStr;
	}

	public void setDescStr(String descStr) {
		this.descStr = descStr;
	}

	public double getDeviceavailability() {
		return deviceavailability;
	}

	public void setDeviceavailability(double deviceavailability) {
		this.deviceavailability = deviceavailability;
	}

	//秒表 部件
	private String one;
	
	private String two;
	
	private String three;
	
	private String operaTip;
	
	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}


	public Integer getDevicemoid() {
		return devicemoid;
	}

	public void setDevicemoid(Integer devicemoid) {
		this.devicemoid = devicemoid;
	}

	public long getInflowpkts() {
		return inflowpkts;
	}

	public void setInflowpkts(long inflowpkts) {
		this.inflowpkts = inflowpkts;
	}

	public long getOutflowpkts() {
		return outflowpkts;
	}

	public void setOutflowpkts(long outflowpkts) {
		this.outflowpkts = outflowpkts;
	}

	public long getInflownpkts() {
		return inflownpkts;
	}

	public void setInflownpkts(long inflownpkts) {
		this.inflownpkts = inflownpkts;
	}

	public long getOutflownpkts() {
		return outflownpkts;
	}

	public void setOutflownpkts(long outflownpkts) {
		this.outflownpkts = outflownpkts;
	}

	public long getUsed() {
		return used;
	}

	public void setUsed(long used) {
		this.used = used;
	}

	public long getReady() {
		return ready;
	}

	public void setReady(long ready) {
		this.ready = ready;
	}

	public long getWait() {
		return wait;
	}

	public void setWait(long wait) {
		this.wait = wait;
	}

	public long getBalloon() {
		return balloon;
	}

	public void setBalloon(long balloon) {
		this.balloon = balloon;
	}

	public long getActive() {
		return active;
	}

	public void setActive(long active) {
		this.active = active;
	}

	public long getOverhead() {
		return overhead;
	}

	public void setOverhead(long overhead) {
		this.overhead = overhead;
	}

	public long getShared() {
		return shared;
	}

	public void setShared(long shared) {
		this.shared = shared;
	}

	public long getSwapped() {
		return swapped;
	}

	public void setSwapped(long swapped) {
		this.swapped = swapped;
	}

	public long getConsumed() {
		return consumed;
	}

	public void setConsumed(long consumed) {
		this.consumed = consumed;
	}

	 

	public Float getReadrequests() {
		return readrequests;
	}

	public void setReadrequests(Float readrequests) {
		this.readrequests = readrequests;
	}

	public void setWritespeed(Float writespeed) {
		this.writespeed = writespeed;
	}

	public Float getWriterequests() {
		return writerequests;
	}

	public void setWriterequests(Float writerequests) {
		this.writerequests = writerequests;
	}
	public Float getReadlatency() {
		return readlatency;
	}

	public void setReadlatency(Float readlatency) {
		this.readlatency = readlatency;
	}


	public Float getWritelatency() {
		return writelatency;
	}

	public void setWritelatency(Float writelatency) {
		this.writelatency = writelatency;
	}

	public Float getBusresets() {
		return busresets;
	}

	public void setBusresets(Float busresets) {
		this.busresets = busresets;
	}
	 
	public Float getPacketsreceived() {
		return packetsreceived;
	}

	public void setPacketsreceived(Float packetsreceived) {
		this.packetsreceived = packetsreceived;
	}

	public Float getPacketstransmitted() {
		return packetstransmitted;
	}

	public void setPacketstransmitted(Float packetstransmitted) {
		this.packetstransmitted = packetstransmitted;
	}

	public long getIdle() {
		return Idle;
	}

	public void setIdle(long idle) {
		Idle = idle;
	}

	public long getSharedcommon() {
		return sharedcommon;
	}

	public void setSharedcommon(long sharedcommon) {
		this.sharedcommon = sharedcommon;
	}

	public long getSwapin() {
		return swapin;
	}

	public void setSwapin(long swapin) {
		this.swapin = swapin;
	}

	public long getSwapout() {
		return swapout;
	}

	public void setSwapout(long swapout) {
		this.swapout = swapout;
	}

	public long getSwapused() {
		return swapused;
	}

	public void setSwapused(long swapused) {
		this.swapused = swapused;
	}

	public long getNormalizedlatency() {
		return normalizedlatency;
	}

	public void setNormalizedlatency(long normalizedlatency) {
		this.normalizedlatency = normalizedlatency;
	}

	public long getIops() {
		return iops;
	}

	public void setIops(long iops) {
		this.iops = iops;
	}
  

	public Float getCommandaborts() {
		return commandaborts;
	}

	public void setCommandaborts(Float commandaborts) {
		this.commandaborts = commandaborts;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getClasslable() {
		return classlable;
	}

	public void setClasslable(String classlable) {
		this.classlable = classlable;
	}

	public String getCollTime() {
		return collTime;
	}

	public void setCollTime(String collTime) {
		this.collTime = collTime;
	}


	public String getIns() {
		return ins;
	}

	public void setIns(String ins) {
		this.ins = ins;
	}

	public String getOuts() {
		return outs;
	}

	public void setOuts(String outs) {
		this.outs = outs;
	}

	public int getIfMOID() {
		return ifMOID;
	}

	public void setIfMOID(int ifMOID) {
		this.ifMOID = ifMOID;
	}

	public Integer getMoclassid() {
		return moclassid;
	}

	public void setMoclassid(Integer moclassid) {
		this.moclassid = moclassid;
	}

	public int getIfDeviceMOID() {
		return ifDeviceMOID;
	}

	public void setIfDeviceMOID(int ifDeviceMOID) {
		this.ifDeviceMOID = ifDeviceMOID;
	}


	public double getDiscards() {
		return discards;
	}

	public void setDiscards(double discards) {
		this.discards = discards;
	}

	public double getErrors() {
		return errors;
	}

	public void setErrors(double errors) {
		this.errors = errors;
	}

	public long getInnpackets() {
		return innpackets;
	}

	public void setInnpackets(long innpackets) {
		this.innpackets = innpackets;
	}

	public long getOutnpackets() {
		return outnpackets;
	}

	public void setOutnpackets(long outnpackets) {
		this.outnpackets = outnpackets;
	}


	public void setOutdiscards(long outdiscards) {
		this.outdiscards = outdiscards;
	}

	public long getInpackets() {
		return inpackets;
	}

	public void setInpackets(long inpackets) {
		this.inpackets = inpackets;
	}

	public long getOutpackets() {
		return outpackets;
	}

	public void setOutpackets(long outpackets) {
		this.outpackets = outpackets;
	}

	
	public double getInerrors() {
		return inerrors;
	}

	public void setInerrors(double inerrors) {
		this.inerrors = inerrors;
	}

	public double getOuterrors() {
		return outerrors;
	}

	public void setOuterrors(double outerrors) {
		this.outerrors = outerrors;
	}

	public double getIndiscards() {
		return indiscards;
	}

	public void setIndiscards(double indiscards) {
		this.indiscards = indiscards;
	}

	public double getOutdiscards() {
		return outdiscards;
	}

	public void setOutdiscards(double outdiscards) {
		this.outdiscards = outdiscards;
	}


	public Float getWritespeed() {
		return writespeed;
	}

	public Float getReadspeed() {
		return readspeed;
	}

	public void setReadspeed(Float readspeed) {
		this.readspeed = readspeed;
	}


	public Float getNetworkusage() {
		return networkusage;
	}

	public void setNetworkusage(Float networkusage) {
		this.networkusage = networkusage;
	}


	public Float getReceivedspeed() {
		return receivedspeed;
	}

	public void setReceivedspeed(Float receivedspeed) {
		this.receivedspeed = receivedspeed;
	}

	 
	public Float getTransmittedspeed() {
		return transmittedspeed;
	}

	public void setTransmittedspeed(Float transmittedspeed) {
		this.transmittedspeed = transmittedspeed;
	}

	public Float getIousage() {
		return iousage;
	}

	public void setIousage(Float iousage) {
		this.iousage = iousage;
	}

	public String getKpiname() {
		return kpiname;
	}

	public void setKpiname(String kpiname) {
		this.kpiname = kpiname;
	}

	public Long getPerfvalue() {
		return perfvalue;
	}

	public void setPerfvalue(Long perfvalue) {
		this.perfvalue = perfvalue;
	}

	public String getCputil() {
		return cputil;
	}

	public void setCputil(String cputil) {
		this.cputil = cputil;
	}

	public String getCPUReady() {
		return CPUReady;
	}

	public void setCPUReady(String cPUReady) {
		CPUReady = cPUReady;
	}

	public String getCPUWait() {
		return CPUWait;
	}

	public void setCPUWait(String cPUWait) {
		CPUWait = cPUWait;
	}

	public long getDiskrrequest() {
		return diskrrequest;
	}

	public void setDiskrrequest(long diskrrequest) {
		this.diskrrequest = diskrrequest;
	}

	public long getDiskwrequest() {
		return diskwrequest;
	}

	public void setDiskwrequest(long diskwrequest) {
		this.diskwrequest = diskwrequest;
	}

	public String getPhymemsize() {
		return phymemsize;
	}

	public void setPhymemsize(String phymemsize) {
		this.phymemsize = phymemsize;
	}

	public String getPhymemfree() {
		return phymemfree;
	}

	public void setPhymemfree(String phymemfree) {
		this.phymemfree = phymemfree;
	}

	public String getPhymemactive() {
		return phymemactive;
	}

	public void setPhymemactive(String phymemactive) {
		this.phymemactive = phymemactive;
	}

	public String getPhymemoverhead() {
		return phymemoverhead;
	}

	public void setPhymemoverhead(String phymemoverhead) {
		this.phymemoverhead = phymemoverhead;
	}

	public String getPhymemshared() {
		return phymemshared;
	}

	public void setPhymemshared(String phymemshared) {
		this.phymemshared = phymemshared;
	}

	public String getVirmemactive() {
		return virmemactive;
	}

	public void setVirmemactive(String virmemactive) {
		this.virmemactive = virmemactive;
	}

	public String getVirmemoverhead() {
		return virmemoverhead;
	}

	public void setVirmemoverhead(String virmemoverhead) {
		this.virmemoverhead = virmemoverhead;
	}

	public String getVirmemshared() {
		return virmemshared;
	}

	public void setVirmemshared(String virmemshared) {
		this.virmemshared = virmemshared;
	}

	public String getVirmemfree() {
		return virmemfree;
	}

	public void setVirmemfree(String virmemfree) {
		this.virmemfree = virmemfree;
	}

	public String getVirmemsize() {
		return virmemsize;
	}

	public void setVirmemsize(String virmemsize) {
		this.virmemsize = virmemsize;
	}

	public long getInflowloss() {
		return inflowloss;
	}

	public void setInflowloss(long inflowloss) {
		this.inflowloss = inflowloss;
	}

	public long getOutflowloss() {
		return outflowloss;
	}

	public void setOutflowloss(long outflowloss) {
		this.outflowloss = outflowloss;
	}

	public long getInflowerrors() {
		return inflowerrors;
	}

	public void setInflowerrors(long inflowerrors) {
		this.inflowerrors = inflowerrors;
	}

	public long getOutflowerrors() {
		return outflowerrors;
	}

	public void setOutflowerrors(long outflowerrors) {
		this.outflowerrors = outflowerrors;
	}

	public long getFullsize() {
		return fullsize;
	}

	public void setFullsize(long fullsize) {
		this.fullsize = fullsize;
	}

	public long getFreesize() {
		return freesize;
	}

	public void setFreesize(long freesize) {
		this.freesize = freesize;
	}

	public int getIfmtu() {
		return ifmtu;
	}

	public void setIfmtu(int ifmtu) {
		this.ifmtu = ifmtu;
	}


	public String getLevelicon() {
		return levelicon;
	}

	public void setLevelicon(String levelicon) {
		this.levelicon = levelicon;
	}

	public String getDevicestatus() {
		return devicestatus;
	}

	public void setDevicestatus(String devicestatus) {
		this.devicestatus = devicestatus;
	}

	public int getAlarmcount() {
		return alarmcount;
	}

	public void setAlarmcount(int alarmcount) {
		this.alarmcount = alarmcount;
	}


	public int getDevicealarmcount() {
		return devicealarmcount;
	}

	public void setDevicealarmcount(int devicealarmcount) {
		this.devicealarmcount = devicealarmcount;
	}

	public int getDevicecount() {
		return devicecount;
	}

	public void setDevicecount(int devicecount) {
		this.devicecount = devicecount;
	}


	public String getCpucores() {
		return cpucores;
	}

	public void setCpucores(String cpucores) {
		this.cpucores = cpucores;
	}

	public int getPhyprocessors() {
		return phyprocessors;
	}

	public void setPhyprocessors(int phyprocessors) {
		this.phyprocessors = phyprocessors;
	}

	public int getLogicalprocessors() {
		return logicalprocessors;
	}

	public void setLogicalprocessors(int logicalprocessors) {
		this.logicalprocessors = logicalprocessors;
	}

	public String getProcessortype() {
		return processortype;
	}

	public void setProcessortype(String processortype) {
		this.processortype = processortype;
	}

	public double getCpuusage() {
		return cpuusage;
	}

	public void setCpuusage(double cpuusage) {
		this.cpuusage = cpuusage;
	}

	public String getConnectionstatus() {
		return connectionstatus;
	}

	public void setConnectionstatus(String connectionstatus) {
		this.connectionstatus = connectionstatus;
	}

	public int getNics() {
		return nics;
	}

	public void setNics(int nics) {
		this.nics = nics;
	}

	public int getHbas() {
		return hbas;
	}

	public void setHbas(int hbas) {
		this.hbas = hbas;
	}

	public String getResmanufacturername() {
		return resmanufacturername;
	}

	public void setResmanufacturername(String resmanufacturername) {
		this.resmanufacturername = resmanufacturername;
	}

	public String getIfdescr() {
		return ifdescr;
	}

	public void setIfdescr(String ifdescr) {
		this.ifdescr = ifdescr;
	}

	public String getIfalias() {
		return ifalias;
	}

	public void setIfalias(String ifalias) {
		this.ifalias = ifalias;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getIftype() {
		return iftype;
	}

	public void setIftype(String iftype) {
		this.iftype = iftype;
	}

	public String getIfadminstatus() {
		return ifadminstatus;
	}

	public void setIfadminstatus(String ifadminstatus) {
		this.ifadminstatus = ifadminstatus;
	}

	public String getIfoperstatus() {
		return ifoperstatus;
	}

	public void setIfoperstatus(String ifoperstatus) {
		this.ifoperstatus = ifoperstatus;
	}

	public int getCpunumber() {
		return cpunumber;
	}

	public void setCpunumber(int cpunumber) {
		this.cpunumber = cpunumber;
	}


	public String getCpuallocated() {
		return cpuallocated;
	}

	public void setCpuallocated(String cpuallocated) {
		this.cpuallocated = cpuallocated;
	}

	public String getMemoryoverhead() {
		return memoryoverhead;
	}

	public void setMemoryoverhead(String memoryoverhead) {
		this.memoryoverhead = memoryoverhead;
	}

	public String getConnectstatus() {
		return connectstatus;
	}

	public void setConnectstatus(String connectstatus) {
		this.connectstatus = connectstatus;
	}

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}

	public Date getCollecttime() {
		return collecttime;
	}

	public void setCollecttime(Date collecttime) {
		this.collecttime = collecttime;
	}

	public String getRescategoryname() {
		return rescategoryname;
	}

	public void setRescategoryname(String rescategoryname) {
		this.rescategoryname = rescategoryname;
	}

	public int getCpucount() {
		return cpucount;
	}

	public void setCpucount(int cpucount) {
		this.cpucount = cpucount;
	}

	public String getIfname() {
		return ifname;
	}

	public void setIfname(String ifname) {
		this.ifname = ifname;
	}

	public String getRawdescr() {
		return rawdescr;
	}

	public void setRawdescr(String rawdescr) {
		this.rawdescr = rawdescr;
	}

	public Integer getVirmemoryusage() {
		return virmemoryusage;
	}

	public void setVirmemoryusage(Integer virmemoryusage) {
		this.virmemoryusage = virmemoryusage;
	}


	public Float getUsagecore() {
		return usagecore;
	}

	public void setUsagecore(Float usagecore) {
		this.usagecore = usagecore;
	}

	public Float getMemusage() {
		return memusage;
	}

	public void setMemusage(Float memusage) {
		this.memusage = memusage;
	}


	public Float getUtilizationcore() {
		return utilizationcore;
	}

	public void setUtilizationcore(Float utilizationcore) {
		this.utilizationcore = utilizationcore;
	}

	public double getCpusage() {
		return cpusage;
	}

	public void setCpusage(double cpusage) {
		this.cpusage = cpusage;
	}

	public String getRestypename() {
		return restypename;
	}

	public void setRestypename(String restypename) {
		this.restypename = restypename;
	}


	public Integer getMoid() {
		return moid;
	}

	public void setMoid(Integer moid) {
		this.moid = moid;
	}

	public String getMoname() {
		return moname;
	}

	public void setMoname(String moname) {
		this.moname = moname == null ? null : moname.trim();
	}

	public String getMoalias() {
		return moalias;
	}

	public void setMoalias(String moalias) {
		this.moalias = moalias == null ? null : moalias.trim();
	}

	public String getOperstatus() {
		return operstatus;
	}

	public void setOperstatus(String operstatus) {
		this.operstatus = operstatus;
	}

	public String getAdminstatus() {
		return adminstatus;
	}

	public void setAdminstatus(String adminstatus) {
		this.adminstatus = adminstatus;
	}

	public Integer getAlarmlevel() {
		return alarmlevel;
	}

	public void setAlarmlevel(Integer alarmlevel) {
		this.alarmlevel = alarmlevel;
	}

	public Integer getDomainid() {
		return domainid;
	}

	public void setDomainid(Integer domainid) {
		this.domainid = domainid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getCreateby() {
		return createby;
	}

	public void setCreateby(Integer createby) {
		this.createby = createby;
	}

	public Integer getUpdateby() {
		return updateby;
	}

	public void setUpdateby(Integer updateby) {
		this.updateby = updateby;
	}

	public String getDeviceip() {
		return deviceip;
	}

	public void setDeviceip(String deviceip) {
		this.deviceip = deviceip == null ? null : deviceip.trim();
	}

	public Integer getNecollectorid() {
		return necollectorid;
	}

	public void setNecollectorid(Integer necollectorid) {
		this.necollectorid = necollectorid;
	}

	public Integer getNemanufacturerid() {
		return nemanufacturerid;
	}

	public void setNemanufacturerid(Integer nemanufacturerid) {
		this.nemanufacturerid = nemanufacturerid;
	}

	public Integer getNecategoryid() {
		return necategoryid;
	}

	public void setNecategoryid(Integer necategoryid) {
		this.necategoryid = necategoryid;
	}

	public String getNeversion() {
		return neversion;
	}

	public void setNeversion(String neversion) {
		this.neversion = neversion == null ? null : neversion.trim();
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os == null ? null : os.trim();
	}

	public String getOsversion() {
		return osversion;
	}

	public void setOsversion(String osversion) {
		this.osversion = osversion == null ? null : osversion.trim();
	}

	public Integer getSnmpversion() {
		return snmpversion;
	}

	public void setSnmpversion(Integer snmpversion) {
		this.snmpversion = snmpversion;
	}

	public Integer getIsmanage() {
		return ismanage;
	}

	public void setIsmanage(Integer ismanage) {
		this.ismanage = ismanage;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public String getAlarmLevelName() {
		return alarmLevelName;
	}

	public void setAlarmLevelName(String alarmLevelName) {
		this.alarmLevelName = alarmLevelName;
	}

	public long getIfOperStatus() {
		return ifOperStatus;
	}

	public void setIfOperStatus(long ifOperStatus) {
		this.ifOperStatus = ifOperStatus;
	}

	public String getPerusage() {
		return perusage;
	}

	public void setPerusage(String perusage) {
		this.perusage = perusage;
	}

	public String getPhymemusage() {
		return phymemusage;
	}

	public void setPhymemusage(String phymemusage) {
		this.phymemusage = phymemusage;
	}

	public String getVirmemusage() {
		return virmemusage;
	}

	public void setVirmemusage(String virmemusage) {
		this.virmemusage = virmemusage;
	}

	public String getDiskusage() {
		return diskusage;
	}

	public void setDiskusage(String diskusage) {
		this.diskusage = diskusage;
	}

	public String getDatastoreusage() {
		return datastoreusage;
	}

	public void setDatastoreusage(String datastoreusage) {
		this.datastoreusage = datastoreusage;
	}

	public String getDatastorefree() {
		return datastorefree;
	}

	public void setDatastorefree(String datastorefree) {
		this.datastorefree = datastorefree;
	}

	public String getDatastorerspeed() {
		return datastorerspeed;
	}

	public void setDatastorerspeed(String datastorerspeed) {
		this.datastorerspeed = datastorerspeed;
	}

	public String getDatastorewspeed() {
		return datastorewspeed;
	}

	public void setDatastorewspeed(String datastorewspeed) {
		this.datastorewspeed = datastorewspeed;
	}

	public long getDatastorerrequest() {
		return datastorerrequest;
	}

	public void setDatastorerrequest(long datastorerrequest) {
		this.datastorerrequest = datastorerrequest;
	}

	public long getDatastorewrequest() {
		return datastorewrequest;
	}

	public void setDatastorewrequest(long datastorewrequest) {
		this.datastorewrequest = datastorewrequest;
	}

	public String getCpuidle() {
		return cpuidle;
	}

	public void setCpuidle(String cpuidle) {
		this.cpuidle = cpuidle;
	}

	public String getCpused() {
		return cpused;
	}

	public void setCpused(String cpused) {
		this.cpused = cpused;
	}

	public String getDiskrate() {
		return diskrate;
	}

	public void setDiskrate(String diskrate) {
		this.diskrate = diskrate;
	}

	public String getDiskrspeed() {
		return diskrspeed;
	}

	public void setDiskrspeed(String diskrspeed) {
		this.diskrspeed = diskrspeed;
	}

	public String getDiskwspeed() {
		return diskwspeed;
	}

	public void setDiskwspeed(String diskwspeed) {
		this.diskwspeed = diskwspeed;
	}

	public String getInflows() {
		return inflows;
	}

	public void setInflows(String inflows) {
		this.inflows = inflows;
	}

	public String getOutflows() {
		return outflows;
	}

	public void setOutflows(String outflows) {
		this.outflows = outflows;
	}

	public String getMemorysize() {
		return memorysize;
	}

	public void setMemorysize(String memorysize) {
		this.memorysize = memorysize;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getDisksize() {
		return disksize;
	}

	public void setDisksize(String disksize) {
		this.disksize = disksize;
	}

	public String getDiskfree() {
		return diskfree;
	}

	public void setDiskfree(String diskfree) {
		this.diskfree = diskfree;
	}

	public String getMemoryusage() {
		return memoryusage;
	}

	public void setMemoryusage(String memoryusage) {
		this.memoryusage = memoryusage;
	}

	public String getHmemorysize() {
		return hmemorysize;
	}

	public void setHmemorysize(String hmemorysize) {
		this.hmemorysize = hmemorysize;
	}

	public String getHmVirMemorysize() {
		return hmVirMemorysize;
	}

	public void setHmVirMemorysize(String hmVirMemorysize) {
		this.hmVirMemorysize = hmVirMemorysize;
	}

	public String getSumdisksize() {
		return sumdisksize;
	}

	public void setSumdisksize(String sumdisksize) {
		this.sumdisksize = sumdisksize;
	}

	public String getCpuvalue() {
		return cpuvalue;
	}

	public void setCpuvalue(String cpuvalue) {
		this.cpuvalue = cpuvalue;
	}

	public String getMemoryvalue() {
		return memoryvalue;
	}

	public void setMemoryvalue(String memoryvalue) {
		this.memoryvalue = memoryvalue;
	}

	public double getViewValue() {
		return viewValue;
	}

	public void setViewValue(double viewValue) {
		this.viewValue = viewValue;
	}

	public double getIfcount() {
		return ifcount;
	}

	public void setIfcount(double ifcount) {
		this.ifcount = ifcount;
	}

	public String getPerMemoryUsage() {
		return perMemoryUsage;
	}

	public void setPerMemoryUsage(String perMemoryUsage) {
		this.perMemoryUsage = perMemoryUsage;
	}

	public String getPerUtil() {
		return perUtil;
	}

	public void setPerUtil(String perUtil) {
		this.perUtil = perUtil;
	}

	public String getDiskused() {
		return diskused;
	}

	public void setDiskused(String diskused) {
		this.diskused = diskused;
	}

	public String getIfUsage() {
		return ifUsage;
	}

	public void setIfUsage(String ifUsage) {
		this.ifUsage = ifUsage;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getInusage() {
		return inusage;
	}

	public void setInusage(String inusage) {
		this.inusage = inusage;
	}

	public String getOutusage() {
		return outusage;
	}

	public void setOutusage(String outusage) {
		this.outusage = outusage;
	}

	public String getIfspeed() {
		return ifspeed;
	}

	public void setIfspeed(String ifspeed) {
		this.ifspeed = ifspeed;
	}

	public String getIfMoName() {
		return ifMoName;
	}

	public void setIfMoName(String ifMoName) {
		this.ifMoName = ifMoName;
	}

	public int getCountdevice() {
		return countdevice;
	}

	public void setCountdevice(int countdevice) {
		this.countdevice = countdevice;
	}

	public int getSourceMOClassID() {
		return sourceMOClassID;
	}

	public void setSourceMOClassID(int sourceMOClassID) {
		this.sourceMOClassID = sourceMOClassID;
	}

	public String getPhyDeviceIP() {
		return phyDeviceIP;
	}

	public void setPhyDeviceIP(String phyDeviceIP) {
		this.phyDeviceIP = phyDeviceIP;
	}

	public Date getUpdateAlarmTime() {
		return updateAlarmTime;
	}

	public void setUpdateAlarmTime(Date updateAlarmTime) {
		this.updateAlarmTime = updateAlarmTime;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public Integer getDoIntervals() {
		return doIntervals;
	}

	public void setDoIntervals(Integer doIntervals) {
		this.doIntervals = doIntervals;
	}

	public Integer getDefDoIntervals() {
		return defDoIntervals;
	}

	public void setDefDoIntervals(Integer defDoIntervals) {
		this.defDoIntervals = defDoIntervals;
	}

	public String getFlows() {
		return flows;
	}

	public void setFlows(String flows) {
		this.flows = flows;
	}

	public String getPerValue() {
		return perValue;
	}

	public void setPerValue(String perValue) {
		this.perValue = perValue;
	}

	public String getClassIcon() {
		return classIcon;
	}

	public void setClassIcon(String classIcon) {
		this.classIcon = classIcon;
	}

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public int getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}

	/**
	 * @return the blackOprateStatus
	 */
	public String getBlackOprateStatus() {
		return blackOprateStatus;
	}

	/**
	 * @param blackOprateStatus the blackOprateStatus to set
	 */
	public void setBlackOprateStatus(String blackOprateStatus) {
		this.blackOprateStatus = blackOprateStatus;
	}

	/**
	 * @return the blackID
	 */
	public Integer getBlackID() {
		return blackID;
	}

	/**
	 * @param blackID the blackID to set
	 */
	public void setBlackID(Integer blackID) {
		this.blackID = blackID;
	}

	/**
	 * @return the blackPortID
	 */
	public Integer getBlackPortID() {
		return blackPortID;
	}

	/**
	 * @param blackPortID the blackPortID to set
	 */
	public void setBlackPortID(Integer blackPortID) {
		this.blackPortID = blackPortID;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the portService
	 */
	public String getPortService() {
		return portService;
	}

	/**
	 * @param portService the portService to set
	 */
	public void setPortService(String portService) {
		this.portService = portService;
	}

	/**
	 * @return the portType
	 */
	public String getPortType() {
		return portType;
	}

	/**
	 * @param portType the portType to set
	 */
	public void setPortType(String portType) {
		this.portType = portType;
	}

	/**
	 * @return the gatewayIP
	 */
	public String getGatewayIP() {
		return gatewayIP;
	}

	/**
	 * @param gatewayIP the gatewayIP to set
	 */
	public void setGatewayIP(String gatewayIP) {
		this.gatewayIP = gatewayIP;
	}

	public String getIfTypeName() {
		return ifTypeName;
	}

	public void setIfTypeName(String ifTypeName) {
		this.ifTypeName = ifTypeName;
	}

	public String getMemType() {
		return memType;
	}

	public void setMeType(String memType) {
		this.memType = memType;
	}
	
}