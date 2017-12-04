package com.fable.insightview.monitor.environmentmonitor.entity;

public class TagKPINameDef {
	public final static String LOWBATTERY = "LowBattery"; 			//电池电量不足
	public final static String TEMPERATURE = "TagTemperature";			//温度
	public final static String MESSAGELOSSRATE = "MessageLossRate";		//消息（报文）丢失率
	public final static String SENSORDISCONNECTED = "SensorDisconnected";		//传感器断开
	public final static String FLUIDDETECTED = "FluidDetected";			//流体检测
	public final static String TAMPER = "TagTamper";					//篡改检测
	public final static String DEWPOINT = "DewPoint";				//露点温度	
	public final static String HUMIDITY = "TagHumidity";				//湿度
	public final static String MOTION = "TagMotion";					//移动检测
	public final static String DOOROPEN = "DoorOpen";				//门打开
	public final static String DRYCONTACTOPEN = "DryContactOpen";			//干接点断开
}
