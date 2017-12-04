package com.fable.insightview.monitor.discover.entity;

public class SNMPDeviceSysObjectIDKey {
    private String deviceoid;

    private Integer pen;

    public String getDeviceoid() {
        return deviceoid;
    }

    public void setDeviceoid(String deviceoid) {
        this.deviceoid = deviceoid == null ? null : deviceoid.trim();
    }

    public Integer getPen() {
        return pen;
    }

    public void setPen(Integer pen) {
        this.pen = pen;
    }
}