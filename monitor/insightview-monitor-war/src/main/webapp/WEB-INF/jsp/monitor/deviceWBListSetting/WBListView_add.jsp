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
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/common/jquery.easyui.extend.js"></script>
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
		src="${pageContext.request.contextPath}/js/monitor/deviceWBListSetting/WBListView_add.js"></script>
		<div class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" id="addWBSetting" style="overflow: auto;height:400px;"> 
		<input type="hidden" id="defineId" >
			<div id="divAlarmDefine" title="黑白名单定义" data-options="closable:false" style="overflow: auto">
				<table id="tblAlarmAdd" class="formtable">
					<tr>
						<td class="title">设备IP：</td>
						<td colspan="3"><input name="deviceIP" id="ipt_deviceIP" validator="{'default':'*','type':'ipAddr'}"></input><b>*</b></td>
					</tr>
					<tr>
						<td class="title">类型：</td>
						<td colspan="3">
						<select name="PortType"  id="ipt_portType" validator="{'default':'*'}">
							<option value="1" selected>接口</option>
							<option value="2">服务端口</option>
						</select><b>*</b></td>
					</tr>
				</table>
				<div id="collectorListDiv">
					<lable>选择下发的采集机(必选):</lable>
					<table id="collectorsList" >
					</table>
				</div>
			</div>
			<div id="divBlackList" title="黑名单" data-options="closable:false" style="overflow: auto;">
				<div class="datas" id="interfaceBlackListDiv">
					<table id="interfaceBlackList" >
					</table>
				</div>
				<div class="datas" id="portBlackListDiv">
					<table id="portBlackList" >
					</table>
				</div>
			</div>

			<div id="divWhiteList" title="白名单" data-options="closable:false" style="overflow: auto;">
			 	<div class="datas" id="interfaceWhiteListDiv">
					<table id="interfaceWhiteList" >
					</table>
				</div>
				<div class="datas" id="portWhiteListDiv">
					<table id="portWhiteList" >
					</table>
				</div>
			</div>
		</div>
		<div class="conditionsBtn">
			<input type="button" id="btnSave" value="确定" onclick="javascript:confirm();"/>
			<input type="button" id="btnClose" value="取消" onclick="javascript:cancle();"/>
		</div>
		<div id='event_select_dlg' class='event_select_dlg'></div>
	</body>
</html>
