package com.fable.insightview.platform.mybatis.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YYYYMMDDDate extends Date {

	private Date orig = null;
	public YYYYMMDDDate(Date date) {
		orig = date;
		if(date != null) {
			setTime(date.getTime());
		}
	}

	public String toString() {
		if(orig == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this);
		}
	}
}
