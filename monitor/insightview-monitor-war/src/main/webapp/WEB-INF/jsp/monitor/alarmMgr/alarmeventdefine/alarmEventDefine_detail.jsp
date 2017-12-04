<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
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
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/alarmeventdefine/alarmEventDefine_detail.js"></script>
		<%
			String alarmDefineID =(String)request.getAttribute("alarmDefineID");
			String recoverAlarmDefineID =(String)request.getAttribute("recoverAlarmDefineID");
			String index =(String)request.getAttribute("index");
		 %>
		<div id="alarmTabs" class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" style="overflow: auto;">
		<input type="hidden" id="defineId" value="<%= alarmDefineID%>"/>
		<input type="hidden" id="recoverId" value="<%= recoverAlarmDefineID%>"/>
		<input type="hidden" id="index" value="<%= index%>"/>
		<input type="hidden" id="isRepeat"/>
		
			<div title="告警事件" data-options="closable:false" style="overflow: hidden;">
					<table id="tblAlarmEventDefineView" class="tableinfo">
						<tr>
							<td>
								<b class="title">告警名称：</b>
								<label id="lbl_alarmName"></label>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<b class="title">告警标题：</b>
								<label id="lbl_alarmTitle" class="x2" />
							</td>
						</tr>
						<tr>
							<td>
								<b class="title">告警源：</b>
								<label id="lbl_moName" />
							</td>
							<td>
								<b class="title">告警分类：</b>
								<label id="lbl_categoryName" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<b class="title">告警标记：</b>
								<label id="lbl_alarmOID" class="x2" />
							</td>
						</tr>
	
						<tr>
							<td>
								<b class="title">告警类型：</b>
								<label id="lbl_alarmTypeName" />
							</td>
							<td>
								<b class="title">告警级别：</b>
								<label id="lbl_alarmLevelName" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<b class="title">告警描述：</b>
								<label id="lbl_description" />
							</td>
	
						</tr>
						<tr>
							<td colspan="2">
								<b class="title">告警手册：</b>
								<label id="lbl_alarmManual" />
							</td>
						</tr>
					</table>
			</div>

			<div id="divRecoverAlarm" title="清除事件" data-options="closable:false" style="overflow: hidden;">
					<table id="tblAlarmEventPairView" class="tableinfo">
					<tr>
						<td>
							<b class="title">告警名称：</b>
							<label id="lbl_recoverAlarmName"></label>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<b class="title">告警标题：</b>
							<label id="lbl_recoverAlarmTitle" class="x2" />
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">告警源：</b>
							<label id="lbl_recoverMoName" />
						</td>
						<td>
							<b class="title">告警分类：</b>
							<label id="lbl_recoverCategoryName" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<b class="title">告警标记：</b>
							<label id="lbl_recoverAlarmOID" class="x2" />
						</td>
					</tr>

					<tr>
						<td>
							<b class="title">告警类型：</b>
							<label id="lbl_recoverAlarmTypeName" />
						</td>
						<td>
							<b class="title">告警级别：</b>
							<label id="lbl_recoverAlarmLevelName" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<b class="title">告警描述：</b>
							<label id="lbl_recoverDescription" />
						</td>

					</tr>
					<tr>
						<td colspan="2">
							<b class="title">告警手册：</b>
							<label id="lbl_recoverAlarmManual" />
						</td>
					</tr>
					</table>
				<!--<h2>清除策略</h2>
				<table id="tblAlarmEventPairView2" class="tableinfo">
					<tr>
						<td>
							<b class="title">时间窗口：</b>
							<label id="lbl_recoverTimeWindow" />
						</td>
						<td>
							<b class="title">启用状态：</b>
							<label id="lbl_recoverIsUsed" />
						</td>
					</tr>
				</table>
			--></div>

			<div title="重复策略" data-options="closable:false" style="overflow: auto;" id="divRepeatView">
				  <table id="tblRepeatView" class="tableinfo1">
					<tr>
						<td><b class="title">告警事件：</b>
						<label id="lbl_repeatAlarmName" class="input"></label>
						</td>
					</tr>
					
					<tr>
						<td><b class="title">时间窗口（秒）：</b>
						<label id="lbl_timeWindow" class="input" />
						</td>
					</tr>

					<tr>
						<td><b class="title">重复告警条件（次）：</b>
						<label id="lbl_alarmOnCount" class="input" />
						</td>
					</tr>
					
					<tr>
						<td><b class="title">告警升级条件（次）：</b>
						<label id="lbl_upgradeOnCount" class="input" />
						</td>
					</tr>
					
					<tr>
						<td><b class="title">启用状态：</b>
						<label id="lbl_isUsed" class="input" />
						</td>
					</tr>
				  </table>
			</div>

			<div id="divAlarmFilter" title="过滤策略" data-options="closable:false" style="overflow-x: hidden;overflow-y: auto;">
					 <table id="tblFileterView" class="tableinfo1">
					<tr>
						<td>
						满足以下：
						<label id="lbl_match" class="input"></label>条件，进行
						<label id="lbl_action" class="input"></label>处理
						</td>
					</tr>
					
				
				  </table>
				  <div class="datas" style="margin-left:20px;">
					<table id="viewFilter"></table>
				</div>
				<div style="margin-left:20px;margin-top:30px;">
					任务类型：
					
					<label id="lbl_isOffline" class="input"></label>
					<div class="datas" id="divOfflineCollector" style="margin-top:10px;">
						<table id="tblOfflineCollector"></table>
					</div>
				</div>
			</div>
		</div>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
		
	</body>
</html>
