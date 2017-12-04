package com.fable.insightview.monitor.discover.entity;

public class SNMPDeviceSysObjectID  {
    private String devicemodelname;

    private String devicetype;

    private String devicenameabbr;

    private String os;

    private String deviceicon;

    private Integer rescategoryid;
    
    private String deviceoid;

    public String getDeviceoid() {
		return deviceoid;
	}

	public void setDeviceoid(String deviceoid) {
		this.deviceoid = deviceoid;
	}

	public Integer getPen() {
		return pen;
	}

	public void setPen(Integer pen) {
		this.pen = pen;
	}

	private Integer pen;

    public String getDevicemodelname() {
        return devicemodelname;
    }

    public void setDevicemodelname(String devicemodelname) {
        this.devicemodelname = devicemodelname == null ? null : devicemodelname.trim();
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype == null ? null : devicetype.trim();
    }

    public String getDevicenameabbr() {
        return devicenameabbr;
    }

    public void setDevicenameabbr(String devicenameabbr) {
        this.devicenameabbr = devicenameabbr == null ? null : devicenameabbr.trim();
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
    }

    public String getDeviceicon() {
        return deviceicon;
    }

    public void setDeviceicon(String deviceicon) {
        this.deviceicon = deviceicon == null ? null : deviceicon.trim();
    }

    public Integer getRescategoryid() {
        return rescategoryid;
    }

    public void setRescategoryid(Integer rescategoryid) {
        this.rescategoryid = rescategoryid;
    }
}