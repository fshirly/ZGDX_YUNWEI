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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor/acUPS/perfUps.js"></script>
		<div>
			<table id="Switch" class="perfTopTable">
			<tr style="background: #ebeff2;"><td   colspan="8"><h2 class="h2">运行参数：</h2></td></tr>
			<tr id="run">
				<td class="perftdTitle">UPS自动重启类型  </td>
					<td>
						<label  id="ipt_autoRestartType">${room.autoRestartType}</label>
					</td>
					<td class="perftdTitle">UPS关闭类型  </td>
					<td>
						<label  id="ipt_shutdownType"  >${room.shutdownType}</label>
					</td>
					<td class="perftdTitle">电池状况 </td>
					<td>
						<label  id="ipt_batCondition" >${room.batCondition}</label>
					</td>
					<td class="perftdTitle">电池状态  </td>
					<td>
						<label  id="ipt_batStatus" >${room.batStatus}</label>
					</td>
				</tr>
				<tr>
					<td class="perftdTitle">电池充电状态 </td>
					<td>
						<label  id="ipt_batChargeStatus" >${room.batChargeStatus}</label>
					</td>
					<td class="perftdTitle">UPS电源电力不足 </td>
					<td>
						<label  id="ipt_secondOnBattery"  >${room.secondOnBattery}</label>
					</td>
					<td class="perftdTitle">电池分钟数 </td>
					<td>
						<label  id="ipt_minutesOnBattery" >${room.minutesOnBattery}分 </label>
					</td>
					<td class="perftdTitle">电量 </td>
					<td>
						<label  id="ipt_charge" >${room.charge} %</label>
					</td>
				</tr>
				<tr>
					<td class="perftdTitle">电池电压  </td>
					<td>
						<label  id="ipt_batteryVol" >${room.batteryVol}V</label>
					</td>
					<td class="perftdTitle">电池电流  </td>
					<td>
						<label  id="ipt_batteryCurrent" >${room.batteryCurrent}A</label>
					</td>
					<td class="perftdTitle">电池温度 </td>
					<td>
						<label  id="ipt_batteryTemperature" >${room.batteryTemperature}℃</label>
					</td>
					<td class="perftdTitle">输入线对 </td>
					<td>
						<label  id="ipt_inputLineBads" > ${room.inputLineBads} </label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">输入线  </td>
					<td>
						<label  id="ipt_inputLines"   > ${room.inputLines} </label>
					</td>
					<td class="perftdTitle">输出线1 </td>
					<td>
						<label  id="ipt_outputLines" >${room.outputLines} </label>
					</td>
					<td class="perftdTitle">输出源  </td>
					<td>
						<label  id="ipt_outputSource">${room.outputSource}</label>
					</td>
					<td class="perftdTitle">旁路线  </td>
					<td>
						<label  id="ipt_bypassLines" >${room.bypassLines}</label>
					</td>
					
				</tr>
				
				<tr>
					<td class="perftdTitle">输入线1频率  </td>
					<td>
						<label  id="ipt_inputLine1Fre" >${room.inputLine1Fre}Hz</label>
					</td>
				
					<td class="perftdTitle">输入线2频率  </td>
					<td>
						<label  id="ipt_inputLine2Fre" >${room.inputLine2Fre}Hz </label>
					</td>
					<td class="perftdTitle">输入线3频率  </td>
					<td>
						<label  id="ipt_inputLine3Fre"  >${room.inputLine3Fre}Hz</label>
					</td>
					
					<td class="perftdTitle">旁路频率  </td>
					<td>
						<label  id="ipt_bypassFre"  >${room.bypassFre}Hz</label>
					</td>
				</tr>
				
				<tr>
						<td class="perftdTitle">输入线1功率  </td>
					<td>
						<label  id="ipt_inputLine1Power"  >${room.inputLine1Power}KW</label>
					</td>
					<td class="perftdTitle">输入线2功率  </td>
					<td>
						<label  id="ipt_inputLine2Power" >${room.inputLine2Power}KW</label>
					</td>
					<td class="perftdTitle">输入线3功率  </td>
					<td>
						<label  id="ipt_inputLine3Power">${room.inputLine3Power}KW</label>
					</td>
					<td class="perftdTitle">旁路线1功率  </td>
					<td>
						<label  id="ipt_bypassLine1Power"  >${room.bypassLine1Power}KW</label>
					</td>
				</tr>
				
				<tr>
				<td class="perftdTitle">输出线1功率 </td>
					<td>
						<label  id="ipt_outputLine1Power" >${room.outputLine1Power}KW</label>
					</td>
					<td class="perftdTitle">输出线2功率 </td>
					<td>
						<label  id="ipt_outputLine2Power" >  ${room.outputLine2Power}KW</label>
					</td>
					<td class="perftdTitle">输出线3功率</td>
					<td>
						<label  id="ipt_outputLine3Power"  >${room.outputLine3Power}KW</label>
					</td>
					<td class="perftdTitle">旁路线2功率 </td>
					<td>
						<label  id="ipt_bypassLine2Power"  >${room.bypassLine2Power}KW</label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">输入线1电压 </td>
					<td>
						<label  id="ipt_inputLine1Vol"  >${room.inputLine1Vol}V</label>
					</td>
					<td class="perftdTitle">输入线2电压 </td>
					<td>
						<label  id="ipt_inputLine2Vol" >${room.inputLine2Vol}V</label>
					</td>
					<td class="perftdTitle">输入线3电压 </td>
					<td>
						<label  id="ipt_inputLine3Vol"  >${room.inputLine3Vol}V</label>
					</td>
					<td class="perftdTitle">旁路线3功率 </td>
					<td>
						<label  id="ipt_bypassLine3Power"  >${room.bypassLine3Power}V</label>
					</td>
				</tr>
				
				<tr>
				<td class="perftdTitle">输出线1电压  </td>
					<td>
						<label  id="ipt_outputLine1Vol"  >${room.outputLine1Vol}V</label>
					</td>
					<td class="perftdTitle">输出线2电压  </td>
					<td>
						<label  id="ipt_outputLine2Vol"  >${room.outputLine2Vol}V</label>
					</td>
					<td class="perftdTitle">输出线3电压  </td>
					<td>
						<label  id="ipt_outputLine3Vol"  >${room.outputLine3Vol}V</label>
					</td>
					<td class="perftdTitle">旁路线1电压 </td>
					<td>
						<label  id="ipt_bypassLine1Vol" >${room.bypassLine1Vol}V</label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">输入线1电流 </td>
					<td>
						<label  id="ipt_inputLine1Cur"  >${room.inputLine1Cur}A</label>
					</td>
					<td class="perftdTitle">输入线2电流  </td>
					<td>
						<label  id="ipt_inputLine2Cur" >${room.inputLine2Cur}A</label>
					</td>
					<td class="perftdTitle">输入线3电流  </td>
					<td>
						<label  id="ipt_inputLine3Cur"  >${room.inputLine3Cur}A</label>
					</td>
					  <td class="perftdTitle">旁路线2电压  </td>
					<td>
						<label  id="ipt_bypassLine2Vol"  >${room.bypassLine2Vol}A</label>
					</td>
					
				</tr>
				
				<tr>
				<td class="perftdTitle">输出线1电流  </td>
					<td>
						<label  id="ipt_outputLine1Cur"  >${room.outputLine1Cur}A</label>
					</td>
					<td class="perftdTitle">输出线2电流  </td>
					<td>
						<label  id="ipt_outputLine2Cur"  >${room.outputLine2Cur}A</label>
					</td>
					<td class="perftdTitle">输出线3电流  </td>
					<td>
						<label  id="ipt_outputLine3Cur"  >${room.outputLine3Cur}A</label>
					</td>
					<td class="perftdTitle">旁路线3电压1  </td>
					<td>
						<label  id="ipt_bypassLine3Vol" >${room.bypassLine3Vol}V</label>
					</td>
					
				</tr>
				
				<tr>
					<td class="perftdTitle">输出线1负载 </td>
					<td>
						<label  id="ipt_outputLine1Load"  >${room.outputLine1Load}%</label>
					</td>
					<td class="perftdTitle">输出线2负载 </td>
					<td>
						<label  id="ipt_outputLine2Load"  >${room.outputLine2Load}%</label>
					</td>
					<td class="perftdTitle">输出线3负载 </td>
					<td>
						<label  id="ipt_outputLine3Load" >${room.outputLine3Load}%</label>
					</td>
					<td class="perftdTitle">旁路线1电流 </td>
					<td>
						<label  id="ipt_bypassLine1Cur" >${room.bypassLine1Cur}A</label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">输出频率 </td>
					<td>
						<label  id="ipt_outputFre"  >${room.outputFre}Hz</label>
					</td>
					<td class="perftdTitle">测试结果 </td>
					<td>
						<label  id="ipt_testResult"  >${room.testResult}</label>
					</td>
					<td class="perftdTitle">电池使用率 </td>
					<td>
						<label  id="ipt_batteryCap" >${room.batteryCap}%</label>
					</td>
					<td class="perftdTitle">旁路线3电流1  </td>
					<td>
						<label  id="ipt_bypassLine3Cur"  >${room.bypassLine3Cur}A</label>
					</td>
				</tr>
				
				<tr>
					<td class="perftdTitle">总输出功率  </td>
					<td>
						<label  id="ipt_outputTotalPower"  >${room.outputTotalPower}KW</label>
					</td>
					<td class="perftdTitle">故障ID</td>
					<td>
						<label  id="ipt_faultID"  >${room.faultID}</label>
					</td>
					<td class="perftdTitle">旁路线2电流 </td>
					<td>
						<label  id="ipt_bypassLine2Cur" >${room.bypassLine2Cur}A</label>
					</td>
				</tr>
				
				<tr style="background: #ebeff2;"><td colspan="8"><h2 class="h2">报警状态：</h2></td></tr>
				<tr>
						<td class="perftdTitle">温度过高 </td>
					<td>
						<input  id="ipt_temperatureHigh" type="hidden" alt="" name="ipt_temperatureHigh"   value="${room.temperatureHigh}"/>
					</td>
					<td class="perftdTitle">输入故障 </td>
					<td>
						<input  id="ipt_inputFault"  type="hidden"  alt="" name="ipt_inputFault" value="${room.inputFault}" />
					</td>
					<td class="perftdTitle">输出故障 </td>
					<td>
						<input  id="ipt_outputFault" type="hidden" alt="" name="ipt_outputFault" value="${room.outputFault}"/>
					</td>
					<td class="perftdTitle">过载 </td>
					<td>
						<input  id="ipt_overLoad" type="hidden" alt="" name="ipt_overLoad" value="${room.overLoad}"/>
					</td>
					
				</tr>
				
				<tr>
					<td class="perftdTitle">旁路故障 </td>
					<td>
						<input  id="ipt_bapassFault" type="hidden" alt="" name="ipt_bapassFault"  value="${room.bapassFault}"/>
					</td>
					<td class="perftdTitle">输出关机</td>
					<td>
						<input  id="ipt_outputShutdown"  type="hidden" alt="" name="ipt_outputShutdown"  value="${room.outputShutdown}"/>
					</td>
					<td class="perftdTitle">UPS关机 </td>
					<td>
						<input  id="ipt_uPSShutdown" type="hidden" alt="" name="ipt_uPSShutdown" value="${room.uPSShutdown}"/>
					</td>
						<td class="perftdTitle">电池充电故障 </td>
					<td>
						<input  id="ipt_chargeFault" type="hidden" alt="" name="ipt_chargeFault" value="${room.chargeFault}" />
					</td>
				</tr>
				<tr>
				<td class="perftdTitle">系统关机 </td>
					<td>
						<input  id="ipt_systemShutdown" type="hidden"  alt="" name="ipt_systemShutdown"  value="${room.systemShutdown}" />
					</td>
					<td class="perftdTitle">风扇故障  </td>
					<td>
						<input  id="ipt_fanFault" type="hidden" alt="" name="ipt_fanFault"  value="${room.fanFault}" />
					</td>
					
					<td class="perftdTitle">保险丝故障 </td>
					<td>
						<input  id="ipt_fuseFault" type="hidden" alt="" name="ipt_fuseFault"  value="${room.fuseFault}" />
					</td>
					<td class="perftdTitle">一般故障 </td>
					<td>
						<input  id="ipt_gereralFault" type="hidden" alt="" name="ipt_gereralFault" value="${room.gereralFault}"/>
					</td>
				</tr>
				<tr>
				<td class="perftdTitle">自动重启 </td>
					<td>
						<input  id="ipt_autoRestart" type="hidden" alt="" name="ipt_autoRestart" value="${room.autoRestart}"/>
					</td>
					
					<td class="perftdTitle">延时关机 </td>
					<td>
						<input  id="ipt_shutdownDelay" type="hidden" alt="" name="ipt_shutdownDelay"   value="${room.shutdownDelay}" />
					</td>
					<td class="perftdTitle">立即关机 </td>
					<td>
						<input  id="ipt_shutdownAtonce" type="hidden" alt="" name="ipt_shutdownAtonce"  value="${room.shutdownAtonce}"/>
					</td>
						<td class="perftdTitle">通信状态 </td>
					<td>
						<input  id="ipt_communicationStatus" type="hidden" alt="" name="ipt_communicationStatus"   value="${room.communicationStatus}"  />
					</td>
					
				</tr>
				
				<tr>
				<td class="perftdTitle">电池故障</td>
					<td>
						<input  id="ipt_batteryFault"  type="hidden" alt="" name="ipt_batteryFault" value="${room.batteryFault}" />
					</td>
					<td class="perftdTitle">电池电压过低 </td>
					<td>
						<input  id="ipt_batteryVolLow" type="hidden" alt="" name="ipt_batteryVolLow" value="${room.batteryVolLow}" />
					</td>
					<td class="perftdTitle">旁路</td>
					<td>
						<input  id="ipt_bypass"  type="hidden" alt="" name="ipt_bypass" value="${room.bypass}" />
					</td>
					<td class="perftdTitle">其它故障 </td>
					<td>
						<input  id="ipt_otherFault" type="hidden" alt="" name="ipt_otherFault" value="${room.otherFault}" />
					</td>
				</tr>
				<tr>
					<td class="perftdTitle">立即测试 </td>
					<td>
						<input  id="ipt_nowTesting" type="hidden" alt="" name="ipt_nowTesting" value="${room.nowTesting}" />
					</td>
				</tr>
			</table>
		</div>	
	</body>
</html>