<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file = "../../common/pageincluded.jsp" %>
<html>
	<head>
	</head>	
	<body>  
	<style type="text/css">
	.perfTopTable {
	margin: 55px auto;
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
	.perfTopTable label{
	  margin-left: 13px;
	  font-size:17px;
	  font-weight:bold;
	}
	</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/acUPS/perfInvtUps.js"></script>
		<div>
			<table id="Switch" class="perfTopTable">
			<tr style="background: #ebeff2;"><td   colspan="8"><h2 class="h2">运行参数：</h2></td></tr>
			<tr>
				<td class="perftdTitle">电池电压 </td>
					<td>
						<label  id="ipt_batteryVoltage" >${invt.batteryVoltage}V</label>
					</td>
					<td class="perftdTitle">电池容量  </td>
					<td>
						<label  id="ipt_shutdownType"  >${invt.batteryCapacity}</label>
					</td>
					<td class="perftdTitle">电池剩余时间</td>
					<td>
						<label  id="ipt_batCondition" >${invt.batteryTimeRemain} 分</label>
					</td>
					<td class="perftdTitle">电池电流 </td>
					<td>
						<label  id="ipt_batStatus"  >${invt.batteryCurrent}A</label>
					</td>
				</tr>
				<tr>
					<td class="perftdTitle">电池温度 </td>
					<td>
						<label  id="ipt_batChargeStatus" >${invt.batteryTemperature}℃</label>
					</td>
					<td class="perftdTitle">输入频率 </td>
					<td>
						<label  id="ipt_secondOnBattery"  >${invt.inputFrequency}Hz</label>
					</td>
					<td class="perftdTitle">输入R相电压 </td>
					<td>
						<label  id="ipt_minutesOnBattery" >${invt.inputVoltageR}V </label>
					</td>
					<td class="perftdTitle">输入S相电压 </td>
					<td>
						<label  id="ipt_charge" >${invt.inputVoltageS}V </label>
					</td>
				</tr>
				<tr>
					<td class="perftdTitle">输入T相电压 </td>
					<td>
						<label  id="ipt_batteryVol" >${invt.inputVoltageT}V</label>
					</td>
					<td class="perftdTitle">输出频率 </td>
					<td>
						<label  id="ipt_batteryCurrent" >${invt.outputFrequency}Hz </label>
					</td>
					<td class="perftdTitle">输出R相电压 </td>
					<td>
						<label  id="ipt_batteryTemperature" >${invt.outputVoltageS}V</label>
					</td>
					<td class="perftdTitle">输出S相电压 </td>
					<td>
						<label  id="ipt_inputLineBads" > ${invt.outputVoltageR}V </label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">输出T相电压</td>
					<td>
						<label  id="ipt_inputLines"   > ${invt.outputVoltageT}V</label>
					</td>
					<td class="perftdTitle">输出R相负载 </td>
					<td>
						<label  id="ipt_outputLines" >${invt.loadPercentageR} </label>
					</td>
					<td class="perftdTitle">输出S相负载  </td>
					<td>
						<label  id="ipt_outputSource">${invt.loadPercentageS}</label>
					</td>
					<td class="perftdTitle">输出T相负载  </td>
					<td>
						<label  id="ipt_bypassLines" >${invt.loadPercentageT}</label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">旁路频率 </td>
					<td>
						<label  id="ipt_inputLine1Fre" >${invt.bypassFrequency}Hz</label>
					</td>
					<td class="perftdTitle">旁路R相电压 </td>
					<td>
						<label  id="ipt_inputLine2Fre" >${invt.bypassVoltageR}V </label>
					</td>
					<td class="perftdTitle">旁路S相电压  </td>
					<td>
						<label  id="ipt_inputLine3Fre"  >${invt.bypassVoltageS}V</label>
					</td>
					
					<td class="perftdTitle">旁路T相电压  </td>
					<td>
						<label  id="ipt_bypassFre"  >${invt.bypassVoltageT}V</label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">AC状态  </td>
					<td>
						<label  id="ipt_aCStatus" >${invt.aCStatus}</label>
					</td>
					
					<td class="perftdTitle">开关方式 </td>
					<td>
						<label  id="ipt_switchMode">${invt.switchMode}</label>
					</td>
					<td class="perftdTitle">直流和整流状态 </td>
					<td>
						<label  id="ipt_inAndOut" >${invt.inAndOut}</label>
					</td>
					<td class="perftdTitle">断路器状态 </td>
					<td>
						<label  id="ipt_breakerStatus"  >${invt.breakerStatus}</label>
					</td>
				</tr>
				<tr>
					<td class="perftdTitle">电池状态</td>
					<td>
						<label  id="ipt_batteryStatus" >${invt.batteryStatus}</label>
					</td>
					<td class="perftdTitle">充电状态</td>
					<td>
						<label  id="ipt_chargeStatus" >${invt.chargeStatus}</label>
					</td>
				</tr>
				 
				<tr style="background: #ebeff2;"><td colspan="8"><h2 class="h2">报警状态：</h2></td></tr>
				<tr>
						<td class="perftdTitle">整流器旋转错误 </td>
					<td>
						<input  id="ipt_rectifierRotationError" type="hidden" alt="" name="ipt_rectifierRotationError"   value="${invt.rectifierRotationError}"/>
					</td>
					<td class="perftdTitle">低电量关机 </td>
					<td>
						<input  id="ipt_lowBatteryShutdown"  type="hidden"  alt="" name="ipt_lowBatteryShutdown" value="${invt.lowBatteryShutdown}" />
					</td>
					<td class="perftdTitle">低电量</td>
					<td>
						<input  id="ipt_lowBattery" type="hidden" alt="" name="ipt_lowBattery" value="${invt.lowBattery}"/>
					</td>
					<td class="perftdTitle">整流器运行 </td>
					<td>
						<input  id="ipt_rectifierOperating" type="hidden" alt="" name="ipt_rectifierOperating" value="${invt.rectifierOperating}"/>
					</td>
				</tr>
				<tr>
					<td class="perftdTitle">旁路状态 </td>
					<td>
						<input  id="ipt_bypassStatus" type="hidden" alt="" name="ipt_bypassStatus" value="${invt.bypassStatus}" />
					</td>
				<td class="perftdTitle">逆变器运行 </td>
					<td>
						<input  id="ipt_inverterOperating" type="hidden" alt="" name="ipt_inverterOperating" value="${invt.inverterOperating}"/>
					</td>
					<td class="perftdTitle">输出故障</td>
					<td>
						<input  id="ipt_outputFail"  type="hidden" alt="" name="ipt_outputFail" value="${invt.outputFail}" />
					</td>
					<td class="perftdTitle">过温</td>
					<td>
						<input  id="ipt_overTemperature" type="hidden" alt="" name="ipt_overTemperature" value="${invt.overTemperature}" />
					</td>
				</tr>
				<tr>
				<td class="perftdTitle">紧急停止 </td>
					<td>
						<input  id="ipt_emergencyStop" type="hidden" alt="" name="ipt_emergencyStop" value="${invt.emergencyStop}"/>
					</td>
					<td class="perftdTitle">高直流关机 </td>
					<td>
						<input  id="ipt_highDCShutdown" type="hidden" alt="" name="ipt_highDCShutdown"   value="${invt.highDCShutdown}" />
					</td>
					<td class="perftdTitle">旁路断路器 </td>
					<td>
						<input  id="ipt_bypassBreaker" type="hidden" alt="" name="ipt_bypassBreaker"  value="${invt.bypassBreaker}"/>
					</td>
						<td class="perftdTitle">过载 </td>
					<td>
						<input  id="ipt_overLoad" type="hidden" alt="" name="ipt_overLoad"   value="${invt.overLoad}"  />
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">短路</td>
					<td>
						<input  id="ipt_shortCircuit"  type="hidden" alt="" name="ipt_shortCircuit" value="${invt.shortCircuit}" />
					</td>
					
				</tr>
					
			</table>
		</div>	
	</body>
</html>