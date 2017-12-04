<%@ page language="java" pageEncoding="utf-8"%>

<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/base.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/style/css/reset.css" />
		<!-- mainframe -->

		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/base64.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/style/style_one/js/main.js"></script>
	</head>

	<body>
<style type="text/css">
#tblFilter tr {
    text-align: center;
}
#tblFilter input {
    text-align: center;
}
</style>
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmeventdefine/alarmEventDefine_add.js"></script>
		<div class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" id="addAlarm" style="overflow: auto;height:400px;"> 
		<input type="hidden" id="defineId" >
			<div id="divAlarmDefine" title="告警事件" data-options="closable:false" style="overflow: auto">
				<table id="tblAlarmAdd" class="formtable">
					<tr>
						<td class="title">告警名称：</td>
						<td colspan="3"><input id="ipt_alarmName" validator="{'default':'*','length':'1-30'}" onblur="javascript:initRepeatAlarmName();"></input><b>*</b></td>
					</tr>
					<tr>
						<td class="title">告警标题：</td>
						<td colspan="3"><input id="ipt_alarmTitle" class="x2" validator="{'default':'*','length':'1-80'}"/><b>*</b></td>
					</tr>
					<tr>
						<td class="title">告警源：</td> 
						<td><input id ="ipt_moID" type="hidden"/><input id ="ipt_deviceIP" type="hidden"/><input id="ipt_moName"  readonly="readonly" onclick="javascript:loadMoRescource(0);"/>
						<a id="btnUnChose" href="javascript:cancelChose();" style="display: none">取消</a>
						</td>
						<td class="title">告警分类：</td>
						<td><input class="easyui-combobox" id="ipt_categoryID" onblur="javascript:setDefualt(0);"/></td>
					</tr>
					<tr>
						<td class="title">告警标记：</td>
						<td colspan="3"><input id="ipt_alarmOIDDefualt" style="width:178px;border-right: 0 none; margin-right: -4px;text-align: right;border-top-right-radius:0px;border-bottom-right-radius:0px;" readonly="readonly" />
						<input id="ipt_alarmOID" style="width: 313px;border-left: 0 none;border-top-left-radius:0px;border-bottom-left-radius:0px;"/><b>*</b></td>
					</tr>

					<tr>
						<td class="title">告警类型：</td>
							<td><input class="easyui-combobox" id="ipt_alarmTypeID"/></td>
							<td class="title">告警级别：</td>
							<td><input class="easyui-combobox" id="ipt_alarmLevelID"/></td>
						
					</tr>
					<tr>
						<td class="title">告警描述：</td>
						<td colspan="3"><textarea rows="3" class="x2" id="ipt_description"></textarea></td>
					</tr>
					<tr>
						<td class="title">告警手册：</td>
						<td colspan="3"><textarea rows="3" class="x2" id="ipt_alarmManual"></textarea></td>
					</tr>
				</table>
			
			</div>
			<div id="divRecoverAlarm" title="清除事件" data-options="closable:false" style="overflow: auto;">
					<table id="tblAlarmEventPairView" class="formtable">
						<tr>
							<td class="title">告警名称：</td>
							<td colspan="3"><input id="ipt_recoverAlarmName" validator="{'default':'*','length':'1-30'}"></input><b>*</b></td>
						</tr>
						<tr>
							<td class="title">告警标题：</td>
							<td colspan="3"><input id="ipt_recoverAlarmTitle" class="x2" validator="{'default':'*','length':'1-80'}"/><b>*</b></td>
						</tr>
						<tr>
							<td class="title">告警源：</td> 
							<td><input id="ipt_recoverMoID" type="hidden"/><input id="ipt_recoverMoName" readonly="readonly"  onclick="javascript:loadMoRescource(1);"/>
							<a id="btnReUnChose" href="javascript:cancelReChose();" style="display: none">取消</a>
							</td>
							<td class="title">告警分类：</td>
							<td><input class="easyui-combobox" id="ipt_recoverCategoryID" /></td>
						</tr>
						<tr>
							<td class="title">告警标记：</td>
							<td colspan="3"><input id="ipt_recoverAlarmOIDDefualt" style="width:178px;border-right: 0 none; margin-right: -4px;text-align: right;" readonly="readonly"/>
							<input id="ipt_recoverAlarmOID" style="width: 313px;border-left: 0 none;"/><b>*</b></td>
						</tr>
	
						<tr>
							<td class="title">告警类型：</td>
							<td><input class="easyui-combobox" id="ipt_recoverAlarmTypeID"/></td>
							<td class="title">告警级别：</td>
							<td><input class="easyui-combobox" id="ipt_recoverAlarmLevelID"/></td>
							
						</tr>
						<tr>
							<td class="title">告警描述：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_recoverDescription"></textarea></td>
						</tr>
						<tr>
							<td class="title">告警手册：</td>
							<td colspan="3"><textarea rows="3" class="x2" id="ipt_recoverDlarmManual"></textarea></td>
						</tr>
					</table>
				<!--<h2>清除策略</h2>
				<table id="tblAlarmEventPairView2" class="formtable">
					<tr>
						<td class="title">时间窗口：</td>
						<td><input id="ipt_recoverTimeWindow" validator="{'reg':'/^\\d+$/','length':'1-11'}" msg="{'reg':'时间窗口只能为数字'}"/><b>*</b></td>
						<td class="title">启用状态：</td>
						<td><input id="ipt_recoverIsUsed" class="input"  type="hidden"/>
						<input type="radio" name="recoverIsUsed" onclick="javascript:editRec()" value="1" checked >&nbsp;启用
						&nbsp;
						<input type="radio" name="recoverIsUsed" onclick="javascript:editRec()" value="0"/>&nbsp;停用
						</td>
					</tr>
				</table>
			--></div>

			<div id="repeatView" title="重复策略" data-options="closable:false" style="overflow: auto;">
			  <table id="tblRepeatView" class="formtable1">
				<tr>
					<td class="title">告警事件：</td>
					<td><input id="ipt_repeatAlarmName" class="input" readonly="readonly" validator="{'default':'*','length':'1-30'}"></input><b>*</b>
					</td>
				</tr>
				
				<tr>
					<td class="title">时间窗口（秒）：</td>
					<td><input id="ipt_timeWindow" class="input" validator="{'reg':'/^\\d+$/','length':'1-11'}" msg="{'reg':'时间窗口只能为数字'}"/><b>*</b>
					</td>
				</tr>

				<tr>
					<td class="title">重复告警条件（次）：</td>
					<td><input id="ipt_alarmOnCount" class="input" validator="{'reg':'/^[+]?[1-9]+\d*$/i','length':'1-11'}" msg="{'reg':'重复告警条件只能为正整数'}"/><b>*</b>
					</td>
				</tr>
				
				<tr>
					<td class="title">告警升级条件（次）：</td>
					<td><input id="ipt_upgradeOnCount" class="input" validator="{'reg':'/^\\d+$/','length':'1-11'}" msg="{'reg':'告警升级条件只能为数字'}"/><b>*</b>
					</td>
				</tr>
				
				<tr>
					<td class="title">启用状态：</td>
					<td><input id="ipt_isUsed" class="input"  type="hidden"/>
					<input type="radio" id="ipt_isUsed0" name="isUsed"  value="1" checked style="width:13px">&nbsp;启用
					&nbsp;
					<input type="radio" id="ipt_isUsed1" name="isUsed" value="0" style="width:13px"/>&nbsp;停用
					</td>
				</tr>
			  </table>
			</div>

			<div title="过滤策略" data-options="closable:false" style="overflow-x: hidden;overflow-y: auto;">
				<h2>过滤条件设置</h2>
				<div id="filterCondition" style="margin-top: 10px;text-align: center;">
				满足以下：
				<select id="ipt_match" class="inputs">
					<option value="all">
						全部
					</option>
					<option value="any">
						任何
					</option>
				</select>
				条件，进行
				<select id="ipt_action" class="inputs">
					<option value="0">
						丢弃事件
					</option>
					<option value="1">
						接受上发
					</option>
				</select>
				处理
				</div>
				
				<div class="datas" style="margin-left:20px;">
					<table id="addFilter"></table>
				</div>
				
				<div style="margin-left:20px;margin-top:30px;">
					任务类型：
					<input id="ipt_isOffline" class="input" type="hidden"/>
					<input type="radio" id="ipt_isOffline0" name="isOffline" value="0" onclick="changeCollector();" checked style="width:13px"/>&nbsp;在线&nbsp;
					<input type="radio" id="ipt_isOffline1" name="isOffline" value="1" onclick="changeCollector();" style="width:13px"/>&nbsp;离线
					<div class="datas" id="divOfflineCollector" style="margin-top:10px;">
						<table id="tblOfflineCollector"></table>
					</div>
				</div>
			</div>
		</div>
		<div class="conditionsBtn">
			<input type="button" id="btnSave" value="确定" onclick="javascript:toAdd();"/>
			<input type="button" id="btnClose" value="取消" onclick="javascript:toCancle();"/>
		</div>
		<div id='event_select_dlg' class='event_select_dlg'></div>
	</body>
</html>
