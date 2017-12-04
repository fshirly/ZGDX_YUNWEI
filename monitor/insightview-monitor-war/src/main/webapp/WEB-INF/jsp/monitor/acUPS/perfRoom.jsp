<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>	
	<body>  
	<style type="text/css">
	.perfTopTable {
/* 	width: 630px; */
	margin: 75px auto;
	}
	.perfTopTable td {
    width: 170px;	
    }
	 .perftdTitle{
		text-align: right;
		min-width: 167px;
		font-size:14px; 
		vertical-align: top;
		padding-top: 6px;
		line-height: 36px;
	}
	.perftdTOP{
		text-align: right;
		min-width: 167px;
		font-size:17px; 
		font-weight:bold;
		vertical-align: top;
		padding-top: 6px;
		line-height: 36px;
	}
	.h2{
		line-height: 24px;
		padding-left: 10px;
	}
	</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/acUPS/perfRoom.js"></script>
		<div>
			<table id="Switch" class="perfTopTable">
			<tr style="background: #ebeff2;"><td   colspan="8"><h2 class="h2">运行参数：</h2></td></tr>
			<tr id="run">
				<td class="perftdTitle">室内温度  </td>
					<td>
						<input  id="ipt_indoorTemperature" type="hidden" name="ipt_indoorTemperature" value="${room.indoorTemperature}" />
					</td>
					<td class="perftdTitle">室内湿度  </td>
					<td>
						<input  id="ipt_indoorHumidity" type="hidden" name="ipt_indoorHumidity"  value="${room.indoorHumidity}" />
					</td>
					<td class="perftdTitle">AC开关状态  </td>
					<td>
						<input  id="ipt_turnOnStatus" type="hidden" name="ipt_turnOnStatus" value="${room.turnOnStatus}" />
					</td>
					<td class="perftdTitle">风扇运行状态  </td>
					<td>
						<input  id="ipt_fansStatus" type="hidden" name="ipt_fansStatus" value="${room.fansStatus}" />
					</td>
				</tr>
				<tr id="runStatus">
					<td class="perftdTitle">制冷运行状态  </td>
					<td>
						<input  id="ipt_refrigerationStatus" type="hidden" name="ipt_refrigerationStatus" value="${room.refrigerationStatus}"  />
					</td>
					<td class="perftdTitle">加热运行状态 </td>
					<td>
						<input  id="ipt_heatStatus" type="hidden" name="ipt_heatStatus" value="${room.heatStatus}" />
					</td>
					<td class="perftdTitle">加湿器运行状态 </td>
					<td>
						<input  id="ipt_humidificationStatus" type="hidden" name="ipt_humidificationStatus" value="${room.humidificationStatus}" />
					</td>
					<td class="perftdTitle">除湿器运行状态 </td>
					<td>
						<input  id="ipt_dehumidificationStatus" type="hidden" name="ipt_dehumidificationStatus" value="${room.dehumidificationStatus}" />
					</td>
				</tr>
				<tr id="system">
				<td class="perftdTitle">运行状态 </td>
					<td>
						<input  id="ipt_systemStatus" type="hidden" name="ipt_systemStatus" value="${room.systemStatus}"/>
					</td>
				</tr>
				<tr style="background: #ebeff2;"><td colspan="8"><h2 class="h2">报警状态：</h2></td></tr>
				<tr>
						<td class="perftdTitle">高压报警 </td>
					<td>
						<input  id="ipt_highVoltageAlarm" type="hidden" alt="" name="ipt_highVoltageAlarm"   value="${room.highVoltageAlarm}"/>
					</td>
					<td class="perftdTitle">高湿度报警 </td>
					<td>
						<input  id="ipt_highHumidityAlarm"  type="hidden"  alt="" name="ipt_highHumidityAlarm" value="${room.highHumidityAlarm}" />
					</td>
					<td class="perftdTitle">排气温度故障 </td>
					<td>
						<input  id="ipt_exhaustTempFailureAlarm" type="hidden" alt="" name="ipt_exhaustTempFailureAlarm" value="${room.exhaustTempFailureAlarm}"/>
					</td>
					<td class="perftdTitle">维护报警 </td>
					<td>
						<input  id="ipt_humidifierMaintenanceAlarm" type="hidden" alt="" name="ipt_humidifierMaintenanceAlarm" value="${room.humidifierMaintenanceAlarm}"/>
					</td>
					
				</tr>
				
				<tr>
					<td class="perftdTitle">低压报警 </td>
					<td>
						<input  id="ipt_lowVoltageAlarm" type="hidden" alt="" name="ipt_lowVoltageAlarm"  value="${room.lowVoltageAlarm}"/>
					</td>
					<td class="perftdTitle">低湿度报警 </td>
					<td>
						<input  id="ipt_lowHumidityAlarm"  type="hidden" alt="" name="ipt_lowHumidityAlarm"  value="${room.lowHumidityAlarm}"/>
					</td>
					<td class="perftdTitle">电源故障报警 </td>
					<td>
						<input  id="ipt_powerFailureAlarm" type="hidden" alt="" name="ipt_powerFailureAlarm" value="${room.powerFailureAlarm}"/>
					</td>
						<td class="perftdTitle">短周期报警 </td>
					<td>
						<input  id="ipt_shortPeriodAlarm" type="hidden" alt="" name="ipt_shortPeriodAlarm" value="${room.shortPeriodAlarm}" />
					</td>
				</tr>
				<tr>
				<td class="perftdTitle">高温报警 </td>
					<td>
						<input  id="ipt_highTemperatureAlarm" type="hidden"  alt="" name="ipt_highTemperatureAlarm"  value="${room.highTemperatureAlarm}" />
					</td>
					<td class="perftdTitle">过滤器维护报警 </td>
					<td>
						<input  id="ipt_filterMaintenanceAlarm" type="hidden" alt="" name="ipt_filterMaintenanceAlarm"  value="${room.filterMaintenanceAlarm}" />
					</td>
					
					<td class="perftdTitle">电源丢失故障 </td>
					<td>
						<input  id="ipt_powerLossFailureAlarm" type="hidden" alt="" name="ipt_powerLossFailureAlarm"  value="${room.powerLossFailureAlarm}" />
					</td>
					<td class="perftdTitle">传感器版丢失报警 </td>
					<td>
						<input  id="ipt_sensorBoardsLossAlarm" type="hidden" alt="" name="ipt_sensorBoardsLossAlarm" value="${room.sensorBoardsLossAlarm}"/>
					</td>
				</tr>
				<tr>
				<td class="perftdTitle">低温报警 </td>
					<td>
						<input  id="ipt_lowTemperatureAlarm" type="hidden" alt="" name="ipt_lowTemperatureAlarm" value="${room.lowTemperatureAlarm}"/>
					</td>
					
					<td class="perftdTitle">水管冻结报警 </td>
					<td>
						<input  id="ipt_coilPipeFreezingAlarm" type="hidden" alt="" name="ipt_coilPipeFreezingAlarm"   value="${room.coilPipeFreezingAlarm}" />
					</td>
					<td class="perftdTitle">加湿器故障报警 </td>
					<td>
						<input  id="ipt_humidifierFailureAlarm" type="hidden" alt="" name="ipt_humidifierFailureAlarm"  value="${room.humidifierFailureAlarm}"/>
					</td>
						<td class="perftdTitle">主风机维护报警 </td>
					<td>
						<input  id="ipt_mainFanMaintenanceAlarm" type="hidden" alt="" name="ipt_mainFanMaintenanceAlarm"   value="${room.mainFanMaintenanceAlarm}"  />
					</td>
					
				</tr>
				
				<tr>
				<td class="perftdTitle">用户定义1报警 </td>
					<td>
						<input  id="ipt_userDefinedAlarm1"  type="hidden" alt="" name="ipt_userDefinedAlarm1" value="${room.userDefinedAlarm1}" />
					</td>
					<td class="perftdTitle">用户定义2报警 </td>
					<td>
						<input  id="ipt_userDefinedAlarm2" type="hidden" alt="" name="ipt_userDefinedAlarm2" value="${room.userDefinedAlarm2}" />
					</td>
					
				</tr>
			</table>
		</div>	
	</body>
</html>