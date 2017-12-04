package com.fable.insightview.monitor.alarmdispatcher.eneity;

import com.fable.insightview.monitor.entity.AlarmNode;
 

/**
 * 实时告警json格式自定义
 *
 */
public class ConvertObj {

	private String c;
	private int i;
	private AlarmNode a;
	private Tag p;

	public Tag getP() {
		return p;
	}

	public void setP(Tag p) {
		this.p = p;
	}

	public AlarmNode getA() {
		return a;
	}

	public void setA(AlarmNode a) {
		this.a = a;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
}