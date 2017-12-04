<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../common/pageincluded.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript"
				src="${pageContext.request.contextPath}/plugin/dTree/dtree.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/monitor/perf/perfGeneralConfig.js"></script>
	</head>
	<body>
		<div class="rightContent">
			<div class="location">
				${navigationBar}
			</div>
			<div class="easyui-tabs" data-options="tabPosition:'left'" id="perfGeneralConfig" >
				<div id="divAlarmSetting" title="告警设置" data-options="closable:false"
					style="overflow: auto;">
					<table id="tblAlarmSetting" class="formtable1">
						<tr>
							<td class="title">
								重复告警条件（次）：
							</td>
							<td>
								<input id="ipt_repeatNum" class="input" validator="{'reg':'/^[+]?[1-9]+\d*$/i','length':'1-11'}" msg="{'reg':'重复告警条件只能为正整数'}"/>
								<b>*</b>
							</td>
						</tr>
	
						<tr>
							<td class="title">
								告警升级条件（次）：
							</td>
							<td>
								<input id="ipt_upgradeNum" class="input" validator="{'reg':'/^\\d+$/','length':'1-11'}" msg="{'reg':'告警升级条件只能为数字'}" />
								<b>*</b>
							</td>
						</tr>
	
						<tr>
							<td class="title">
								时间窗口（秒）：
							</td>
							<td>
								<input id="ipt_timeWindow" class="input" validator="{'reg':'/^\\d+$/','length':'1-11'}" msg="{'reg':'时间窗口只能为数字'}" />
								<b>*</b>
							</td>
						</tr>
					</table>
					<div class="conditionsBtn">
						<a onclick="doSetAlarmRule();">确定</a>
					</div>
				</div>
				
				<div id="divPerfSetting" title="采集周期设置" data-options="closable:false"
					style="overflow: auto;">
					<input type="hidden" id="className"/>
					<input type="hidden" id="collectPeriod"/>
					<input type="hidden" id="classLable"/>
					<div class="datas" style="top:40px">
						<table id="tblCollectPeriodLst">
						</table>
					</div>
				</div>
				
			</div>
				<div id="divMObject" class="easyui-window" maximizable="false"
				  collapsible="false" minimizable="false" closed="true" modal="true"
				  title="选择对象类型" style="width: 400px; height: 450px;">
					<div id="dataMObjectTreeDiv" class="dtree"
						style="width: 100%; height: 200px;">
					</div>
				</div>
		</div>
	</body>
</html>