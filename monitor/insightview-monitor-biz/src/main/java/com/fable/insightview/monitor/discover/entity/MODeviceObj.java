package com.fable.insightview.monitor.discover.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MODeviceObj {
	
//	"restype":"device","resid":0,"moclassid":"1","moid":1,”content”:{"moname":"dev1","moalias":"fabdev",
//	"operstatus":"未知","domain":"南京","createtime":"2014-08-18 18:12:12","deviceid":1,"nemanufacturer":"华为",
//	"necategory":"h222","os":"linux"

	private Integer id;
	
    private Integer moid;

    private String moname;
    
    private Integer restype;
     
	private Integer resid;

    private String moalias;

    private Integer operstatus;
    
    private String operstatusdetail;

    private Integer adminstatus;

    private Integer alarmlevel;
    
    private String alermlevelInfo;

    private Integer domainid;
    
    private String domainName;
    
    private Integer moClassId;
    
	private Date createtime;

    private Date lastupdatetime;

    private Integer createby;

    private Integer updateby;

    private String deviceip;
    
    private String vcenterip;
  
    private Integer necollectorid;
    
    private String necollectoridinfo;
    
    private Integer necategoryid;
    
    private String necategoryname;

    private String neversion;

    private String os; 

    private String osversion;

    private Integer snmpversion;

    private Integer ismanage; 
   
    private String ismanageinfo;
    
    private String devicetype;
    
    private String devicetypeDescr;
    
	private Integer nemanufacturerid;
    
    private String nemanufacturername;
    
    private String devicemodelname;
    
    private Integer taskId;
    
    private String levelColor;
    
    private String levelIcon;
    
    private Integer resCategoryID;
    
    private Date updateAlarmTime;
	private String durationTime;//持续时间
	private Integer doIntervals; 
	private Integer defDoIntervals;
	private Date collectTime;
	private String operaTip;
	
	//TOPO zhukai,用于设备发现导出结果时，树形结构以设备类型展示
	private String classLable;
	
	//所有组件类型以及其对应的资源ID 用于设备同步到cmdb
	//private List<Map<String, Object>> allComponent;
	
	//TODO 同步应用系统时，增加字段
	private String url;
	private String lineOnTime;
	
	//TODO 同步应用服务时，增加字段
	private String siteName;
	private String ipAddr;
	private String port;
	
    public String getDevicetypeDescr() {
		return devicetypeDescr;
	}

	public void setDevicetypeDescr(String devicetypeDescr) {
		this.devicetypeDescr = devicetypeDescr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRestype() {
		return restype;
	}

	public void setRestype(Integer restype) {
		this.restype = restype;
	}

	public Integer getResid() {
		return resid;
	}

	public void setResid(Integer resid) {
		this.resid = resid;
	}
    
    public Integer getTaskId() {
		return taskId;
	}
    
    public Integer getMoClassId() {
		return moClassId;
	}

	public void setMoClassId(Integer moClassId) {
		this.moClassId = moClassId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
 
    public String getOperstatusdetail() {
		return operstatusdetail;
	}

	public void setOperstatusdetail(String operstatusdetail) {
		this.operstatusdetail = operstatusdetail;
	}

	public String getNecollectoridinfo() {
		return necollectoridinfo;
	}

	public void setNecollectoridinfo(String necollectoridinfo) {
		this.necollectoridinfo = necollectoridinfo;
	}
  
    public String getNemanufacturername() {
		return nemanufacturername;
	}

	public void setNemanufacturername(String nemanufacturername) {
		this.nemanufacturername = nemanufacturername;
	}

	public String getNecategoryname() {
		return necategoryname;
	}

	public void setNecategoryname(String necategoryname) {
		this.necategoryname = necategoryname;
	}

    public String getAlermlevelInfo() {
		return alermlevelInfo;
	}

	public void setAlermlevelInfo(String alermlevelInfo) {
		this.alermlevelInfo = alermlevelInfo;
	}

	public String getIsmanageinfo() {
		return ismanageinfo;
	}

	public void setIsmanageinfo(String ismanageinfo) {
		this.ismanageinfo = ismanageinfo;
	}
  
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
  
	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getDevicemodelname() {
		return devicemodelname;
	}

	public void setDevicemodelname(String devicemodelname) {
		this.devicemodelname = devicemodelname;
	}

//  private SNMPDeviceSysObjectID snmpDeviceSysObject;
 
//	public SNMPDeviceSysObjectID getSnmpDeviceSysObject() {
//		return snmpDeviceSysObject;
//	}
//
//	public void setSnmpDeviceSysObject(SNMPDeviceSysObjectID snmpDeviceSysObject) {
//		this.snmpDeviceSysObject = snmpDeviceSysObject;
//	}

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

    public Integer getOperstatus() {
        return operstatus;
    }

    public void setOperstatus(Integer operstatus) {
        this.operstatus = operstatus;
    }

    public Integer getAdminstatus() {
        return adminstatus;
    }

    public void setAdminstatus(Integer adminstatus) {
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

    public Date getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(Date lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
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

	public String getLevelColor() {
		return levelColor;
	}

	public void setLevelColor(String levelColor) {
		this.levelColor = levelColor;
	}

	public String getLevelIcon() {
		return levelIcon;
	}

	public void setLevelIcon(String levelIcon) {
		this.levelIcon = levelIcon;
	}

	public String getVcenterip() {
		return vcenterip;
	}

	public void setVcenterip(String vcenterip) {
		this.vcenterip = vcenterip;
	}

	public Integer getResCategoryID() {
		return resCategoryID;
	}

	public void setResCategoryID(Integer resCategoryID) {
		this.resCategoryID = resCategoryID;
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

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}

	public String getOperaTip() {
		return operaTip;
	}

	public void setOperaTip(String operaTip) {
		this.operaTip = operaTip;
	}

	public String getClassLable() {
		return classLable;
	}

	public void setClassLable(String classLable) {
		this.classLable = classLable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLineOnTime() {
		return lineOnTime;
	}

	public void setLineOnTime(String lineOnTime) {
		this.lineOnTime = lineOnTime;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/*public List<Map<String, Object>> getAllComponent() {
		return allComponent;
	}

	public void setAllComponent(List<Map<String, Object>> allComponent) {
		this.allComponent = allComponent;
	}*/
}