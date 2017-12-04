package com.fable.insightview.monitor.harvester.entity;
import org.apache.ibatis.type.Alias;

@Alias("sysServiceInfo")
public class SysServiceInfo {
    private Integer serviceid;

    private String servicename;

    private String processname;

    private Integer servicetype;

    private Integer udpport;

    private Integer tcpport;

    private String bindir;

    private String binnames;

    private String confdir;

    private String confnames;

    private String lognames;

    private String servicedescr;

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename == null ? null : servicename.trim();
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname == null ? null : processname.trim();
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public Integer getUdpport() {
        return udpport;
    }

    public void setUdpport(Integer udpport) {
        this.udpport = udpport;
    }

    public Integer getTcpport() {
        return tcpport;
    }

    public void setTcpport(Integer tcpport) {
        this.tcpport = tcpport;
    }

    public String getBindir() {
        return bindir;
    }

    public void setBindir(String bindir) {
        this.bindir = bindir == null ? null : bindir.trim();
    }

    public String getBinnames() {
        return binnames;
    }

    public void setBinnames(String binnames) {
        this.binnames = binnames == null ? null : binnames.trim();
    }

    public String getConfdir() {
        return confdir;
    }

    public void setConfdir(String confdir) {
        this.confdir = confdir == null ? null : confdir.trim();
    }

    public String getConfnames() {
        return confnames;
    }

    public void setConfnames(String confnames) {
        this.confnames = confnames == null ? null : confnames.trim();
    }

    public String getLognames() {
        return lognames;
    }

    public void setLognames(String lognames) {
        this.lognames = lognames == null ? null : lognames.trim();
    }

    public String getServicedescr() {
        return servicedescr;
    }

    public void setServicedescr(String servicedescr) {
        this.servicedescr = servicedescr == null ? null : servicedescr.trim();
    }
}