package com.fable.insightview.monitor.portal.service;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {
	public static Timestamp getCurrDate() {
    	Date d = new Date();
    	Timestamp ts = new Timestamp(d.getTime());
    	return ts;
    }
    
    public static Timestamp getDate(Object oTime) {
    	long ll = Long.parseLong(oTime.toString());
    	Timestamp ts = new Timestamp(ll);
    	return ts;
    }
}
