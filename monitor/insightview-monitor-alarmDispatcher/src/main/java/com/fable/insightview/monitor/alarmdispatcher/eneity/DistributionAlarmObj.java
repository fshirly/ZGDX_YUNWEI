package com.fable.insightview.monitor.alarmdispatcher.eneity;

import java.sql.Timestamp;

public class DistributionAlarmObj {
	private String transferor;
	private Timestamp transferTime;
	private int opType;
	private int currentCount;
	private Timestamp firstTransferTime;
	private CustomAlarmNode content;
	private String dispatchID;

	public String getDispatchID() {
		return dispatchID;
	}

	public void setDispatchID(String dispatchID) {
		this.dispatchID = dispatchID;
	}

	public String getTransferor() {
		return transferor;
	}

	public void setTransferor(String transferor) {
		this.transferor = transferor;
	}

	public Timestamp getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Timestamp transferTime) {
		this.transferTime = transferTime;
	}

	public int getOpType() {
		return opType;
	}

	public void setOpType(int opType) {
		this.opType = opType;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public Timestamp getFirstTransferTime() {
		return firstTransferTime;
	}

	public void setFirstTransferTime(Timestamp firstTransferTime) {
		this.firstTransferTime = firstTransferTime;
	}

	public CustomAlarmNode getContent() {
		return content;
	}

	public void setContent(CustomAlarmNode content) {
		this.content = content;
	}
}