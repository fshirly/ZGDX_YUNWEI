package com.fable.insightview.monitor.AcUPS.entity;

public class perfEastUpsBean {

	private Integer id;
	private Integer moid;
	private String collectTime;
	private int temperatureHigh;
	private int inputFault;
	private int outputFault;
	private int overLoad;
	private int bapassFault;
	private int outputShutdown;
	private int uPSShutdown;
	private int chargeFault;
	private int systemShutdown;
	private int fanFault;
	private int fuseFault;
	private int gereralFault;
	private int autoRestart;
	private int shutdownDelay;
	private int shutdownAtonce;
	private int communicationStatus;
	private int batteryFault;
	private int batteryVolLow;
	private int bypass;
	private int otherFault;
	private int nowTesting;
	
	//TODO 以下参数为UPS运行参数
	private int autoRestartType;
	private int shutdownType;
	private int batCondition;
	private int batStatus;
	private int batChargeStatus;
	private int secondOnBattery;
	private int minutesOnBattery;
	private int charge;
	private int batteryVol;
	private int batteryCurrent;
	private int batteryTemperature;
	private int inputLineBads;
	private int inputLines;
	private int inputLine1Fre;
	private int inputLine1Vol;
	private int inputLine1Cur;
	private int inputLine1Power;
	private int inputLine2Fre;
	private int inputLine2Vol;
	private int inputLine2Cur;
	private int inputLine2Power;
	private int inputLine3Fre;
	private int inputLine3Vol;
	private int inputLine3Cur;
	private int inputLine3Power;
	private int outputSource;
	private int outputFre;
	private int outputLines;
	private int outputLine1Vol;
	private int outputLine1Cur;
	private int outputLine1Power;
	private int outputLine1Load;
	private int outputLine2Vol;
	private int outputLine2Cur;
	private int outputLine2Power;
	private int outputLine2Load;
	private int outputLine3Vol;
	private int outputLine3Cur;
	private int outputLine3Power;
	private int outputLine3Load;
	private int bypassFre;
	private int bypassLines	;
	private int bypassLine1Vol;
	private int bypassLine1Cur;
	private int bypassLine1Power;
	private int bypassLine2Vol;
	private int bypassLine2Cur;
	private int bypassLine2Power;
	private int bypassLine3Vol;
	private int bypassLine3Cur;
	private int bypassLine3Power;
	private int testResult	;
	private int outputTotalPower;
	private int batteryCap;
	private int faultID;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMoid() {
		return moid;
	}
	public void setMoid(Integer moid) {
		this.moid = moid;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public int getTemperatureHigh() {
		return temperatureHigh;
	}
	public void setTemperatureHigh(int temperatureHigh) {
		this.temperatureHigh = temperatureHigh;
	}
	public int getInputFault() {
		return inputFault;
	}
	public void setInputFault(int inputFault) {
		this.inputFault = inputFault;
	}
	public int getOutputFault() {
		return outputFault;
	}
	public void setOutputFault(int outputFault) {
		this.outputFault = outputFault;
	}
	public int getOverLoad() {
		return overLoad;
	}
	public void setOverLoad(int overLoad) {
		this.overLoad = overLoad;
	}
	public int getBapassFault() {
		return bapassFault;
	}
	public void setBapassFault(int bapassFault) {
		this.bapassFault = bapassFault;
	}
	public int getOutputShutdown() {
		return outputShutdown;
	}
	public void setOutputShutdown(int outputShutdown) {
		this.outputShutdown = outputShutdown;
	}
	public int getuPSShutdown() {
		return uPSShutdown;
	}
	public void setuPSShutdown(int uPSShutdown) {
		this.uPSShutdown = uPSShutdown;
	}
	public int getChargeFault() {
		return chargeFault;
	}
	public void setChargeFault(int chargeFault) {
		this.chargeFault = chargeFault;
	}
	public int getSystemShutdown() {
		return systemShutdown;
	}
	public void setSystemShutdown(int systemShutdown) {
		this.systemShutdown = systemShutdown;
	}
	public int getFanFault() {
		return fanFault;
	}
	public void setFanFault(int fanFault) {
		this.fanFault = fanFault;
	}
	public int getFuseFault() {
		return fuseFault;
	}
	public void setFuseFault(int fuseFault) {
		this.fuseFault = fuseFault;
	}
	public int getGereralFault() {
		return gereralFault;
	}
	public void setGereralFault(int gereralFault) {
		this.gereralFault = gereralFault;
	}
	public int getAutoRestart() {
		return autoRestart;
	}
	public void setAutoRestart(int autoRestart) {
		this.autoRestart = autoRestart;
	}
	public int getShutdownDelay() {
		return shutdownDelay;
	}
	public void setShutdownDelay(int shutdownDelay) {
		this.shutdownDelay = shutdownDelay;
	}
	public int getShutdownAtonce() {
		return shutdownAtonce;
	}
	public void setShutdownAtonce(int shutdownAtonce) {
		this.shutdownAtonce = shutdownAtonce;
	}
	public int getCommunicationStatus() {
		return communicationStatus;
	}
	public void setCommunicationStatus(int communicationStatus) {
		this.communicationStatus = communicationStatus;
	}
	public int getBatteryFault() {
		return batteryFault;
	}
	public void setBatteryFault(int batteryFault) {
		this.batteryFault = batteryFault;
	}
	public int getBatteryVolLow() {
		return batteryVolLow;
	}
	public void setBatteryVolLow(int batteryVolLow) {
		this.batteryVolLow = batteryVolLow;
	}
	public int getBypass() {
		return bypass;
	}
	public void setBypass(int bypass) {
		this.bypass = bypass;
	}
	public int getOtherFault() {
		return otherFault;
	}
	public void setOtherFault(int otherFault) {
		this.otherFault = otherFault;
	}
	public int getNowTesting() {
		return nowTesting;
	}
	public void setNowTesting(int nowTesting) {
		this.nowTesting = nowTesting;
	}
	public int getAutoRestartType() {
		return autoRestartType;
	}
	public void setAutoRestartType(int autoRestartType) {
		this.autoRestartType = autoRestartType;
	}
	public int getShutdownType() {
		return shutdownType;
	}
	public void setShutdownType(int shutdownType) {
		this.shutdownType = shutdownType;
	}
	public int getBatCondition() {
		return batCondition;
	}
	public void setBatCondition(int batCondition) {
		this.batCondition = batCondition;
	}
	public int getBatStatus() {
		return batStatus;
	}
	public void setBatStatus(int batStatus) {
		this.batStatus = batStatus;
	}
	public int getBatChargeStatus() {
		return batChargeStatus;
	}
	public void setBatChargeStatus(int batChargeStatus) {
		this.batChargeStatus = batChargeStatus;
	}
	public int getSecondOnBattery() {
		return secondOnBattery;
	}
	public void setSecondOnBattery(int secondOnBattery) {
		this.secondOnBattery = secondOnBattery;
	}
	public int getMinutesOnBattery() {
		return minutesOnBattery;
	}
	public void setMinutesOnBattery(int minutesOnBattery) {
		this.minutesOnBattery = minutesOnBattery;
	}
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	public int getBatteryVol() {
		return batteryVol;
	}
	public void setBatteryVol(int batteryVol) {
		this.batteryVol = batteryVol;
	}
	public int getBatteryCurrent() {
		return batteryCurrent;
	}
	public void setBatteryCurrent(int batteryCurrent) {
		this.batteryCurrent = batteryCurrent;
	}
	public int getBatteryTemperature() {
		return batteryTemperature;
	}
	public void setBatteryTemperature(int batteryTemperature) {
		this.batteryTemperature = batteryTemperature;
	}
	public int getInputLineBads() {
		return inputLineBads;
	}
	public void setInputLineBads(int inputLineBads) {
		this.inputLineBads = inputLineBads;
	}
	public int getInputLines() {
		return inputLines;
	}
	public void setInputLines(int inputLines) {
		this.inputLines = inputLines;
	}
	public int getInputLine1Fre() {
		return inputLine1Fre;
	}
	public void setInputLine1Fre(int inputLine1Fre) {
		this.inputLine1Fre = inputLine1Fre;
	}
	public int getInputLine1Vol() {
		return inputLine1Vol;
	}
	public void setInputLine1Vol(int inputLine1Vol) {
		this.inputLine1Vol = inputLine1Vol;
	}
	public int getInputLine1Cur() {
		return inputLine1Cur;
	}
	public void setInputLine1Cur(int inputLine1Cur) {
		this.inputLine1Cur = inputLine1Cur;
	}
	public int getInputLine1Power() {
		return inputLine1Power;
	}
	public void setInputLine1Power(int inputLine1Power) {
		this.inputLine1Power = inputLine1Power;
	}
	public int getInputLine2Fre() {
		return inputLine2Fre;
	}
	public void setInputLine2Fre(int inputLine2Fre) {
		this.inputLine2Fre = inputLine2Fre;
	}
	public int getInputLine2Vol() {
		return inputLine2Vol;
	}
	public void setInputLine2Vol(int inputLine2Vol) {
		this.inputLine2Vol = inputLine2Vol;
	}
	public int getInputLine2Cur() {
		return inputLine2Cur;
	}
	public void setInputLine2Cur(int inputLine2Cur) {
		this.inputLine2Cur = inputLine2Cur;
	}
	public int getInputLine2Power() {
		return inputLine2Power;
	}
	public void setInputLine2Power(int inputLine2Power) {
		this.inputLine2Power = inputLine2Power;
	}
	public int getInputLine3Fre() {
		return inputLine3Fre;
	}
	public void setInputLine3Fre(int inputLine3Fre) {
		this.inputLine3Fre = inputLine3Fre;
	}
	public int getInputLine3Vol() {
		return inputLine3Vol;
	}
	public void setInputLine3Vol(int inputLine3Vol) {
		this.inputLine3Vol = inputLine3Vol;
	}
	public int getInputLine3Cur() {
		return inputLine3Cur;
	}
	public void setInputLine3Cur(int inputLine3Cur) {
		this.inputLine3Cur = inputLine3Cur;
	}
	public int getInputLine3Power() {
		return inputLine3Power;
	}
	public void setInputLine3Power(int inputLine3Power) {
		this.inputLine3Power = inputLine3Power;
	}
	public int getOutputSource() {
		return outputSource;
	}
	public void setOutputSource(int outputSource) {
		this.outputSource = outputSource;
	}
	public int getOutputFre() {
		return outputFre;
	}
	public void setOutputFre(int outputFre) {
		this.outputFre = outputFre;
	}
	public int getOutputLines() {
		return outputLines;
	}
	public void setOutputLines(int outputLines) {
		this.outputLines = outputLines;
	}
	public int getOutputLine1Vol() {
		return outputLine1Vol;
	}
	public void setOutputLine1Vol(int outputLine1Vol) {
		this.outputLine1Vol = outputLine1Vol;
	}
	public int getOutputLine1Cur() {
		return outputLine1Cur;
	}
	public void setOutputLine1Cur(int outputLine1Cur) {
		this.outputLine1Cur = outputLine1Cur;
	}
	public int getOutputLine1Power() {
		return outputLine1Power;
	}
	public void setOutputLine1Power(int outputLine1Power) {
		this.outputLine1Power = outputLine1Power;
	}
	public int getOutputLine1Load() {
		return outputLine1Load;
	}
	public void setOutputLine1Load(int outputLine1Load) {
		this.outputLine1Load = outputLine1Load;
	}
	public int getOutputLine2Vol() {
		return outputLine2Vol;
	}
	public void setOutputLine2Vol(int outputLine2Vol) {
		this.outputLine2Vol = outputLine2Vol;
	}
	public int getOutputLine2Cur() {
		return outputLine2Cur;
	}
	public void setOutputLine2Cur(int outputLine2Cur) {
		this.outputLine2Cur = outputLine2Cur;
	}
	public int getOutputLine2Power() {
		return outputLine2Power;
	}
	public void setOutputLine2Power(int outputLine2Power) {
		this.outputLine2Power = outputLine2Power;
	}
	public int getOutputLine2Load() {
		return outputLine2Load;
	}
	public void setOutputLine2Load(int outputLine2Load) {
		this.outputLine2Load = outputLine2Load;
	}
	public int getOutputLine3Vol() {
		return outputLine3Vol;
	}
	public void setOutputLine3Vol(int outputLine3Vol) {
		this.outputLine3Vol = outputLine3Vol;
	}
	public int getOutputLine3Cur() {
		return outputLine3Cur;
	}
	public void setOutputLine3Cur(int outputLine3Cur) {
		this.outputLine3Cur = outputLine3Cur;
	}
	public int getOutputLine3Power() {
		return outputLine3Power;
	}
	public void setOutputLine3Power(int outputLine3Power) {
		this.outputLine3Power = outputLine3Power;
	}
	public int getOutputLine3Load() {
		return outputLine3Load;
	}
	public void setOutputLine3Load(int outputLine3Load) {
		this.outputLine3Load = outputLine3Load;
	}
	public int getBypassFre() {
		return bypassFre;
	}
	public void setBypassFre(int bypassFre) {
		this.bypassFre = bypassFre;
	}
	public int getBypassLines() {
		return bypassLines;
	}
	public void setBypassLines(int bypassLines) {
		this.bypassLines = bypassLines;
	}
	public int getBypassLine1Vol() {
		return bypassLine1Vol;
	}
	public void setBypassLine1Vol(int bypassLine1Vol) {
		this.bypassLine1Vol = bypassLine1Vol;
	}
	public int getBypassLine1Cur() {
		return bypassLine1Cur;
	}
	public void setBypassLine1Cur(int bypassLine1Cur) {
		this.bypassLine1Cur = bypassLine1Cur;
	}
	public int getBypassLine1Power() {
		return bypassLine1Power;
	}
	public void setBypassLine1Power(int bypassLine1Power) {
		this.bypassLine1Power = bypassLine1Power;
	}
	public int getBypassLine2Vol() {
		return bypassLine2Vol;
	}
	public void setBypassLine2Vol(int bypassLine2Vol) {
		this.bypassLine2Vol = bypassLine2Vol;
	}
	public int getBypassLine2Cur() {
		return bypassLine2Cur;
	}
	public void setBypassLine2Cur(int bypassLine2Cur) {
		this.bypassLine2Cur = bypassLine2Cur;
	}
	public int getBypassLine2Power() {
		return bypassLine2Power;
	}
	public void setBypassLine2Power(int bypassLine2Power) {
		this.bypassLine2Power = bypassLine2Power;
	}
	public int getBypassLine3Vol() {
		return bypassLine3Vol;
	}
	public void setBypassLine3Vol(int bypassLine3Vol) {
		this.bypassLine3Vol = bypassLine3Vol;
	}
	public int getBypassLine3Cur() {
		return bypassLine3Cur;
	}
	public void setBypassLine3Cur(int bypassLine3Cur) {
		this.bypassLine3Cur = bypassLine3Cur;
	}
	public int getBypassLine3Power() {
		return bypassLine3Power;
	}
	public void setBypassLine3Power(int bypassLine3Power) {
		this.bypassLine3Power = bypassLine3Power;
	}
	public int getTestResult() {
		return testResult;
	}
	public void setTestResult(int testResult) {
		this.testResult = testResult;
	}
	public int getOutputTotalPower() {
		return outputTotalPower;
	}
	public void setOutputTotalPower(int outputTotalPower) {
		this.outputTotalPower = outputTotalPower;
	}
	public int getBatteryCap() {
		return batteryCap;
	}
	public void setBatteryCap(int batteryCap) {
		this.batteryCap = batteryCap;
	}
	public int getFaultID() {
		return faultID;
	}
	public void setFaultID(int faultID) {
		this.faultID = faultID;
	}
	
	
	
}
