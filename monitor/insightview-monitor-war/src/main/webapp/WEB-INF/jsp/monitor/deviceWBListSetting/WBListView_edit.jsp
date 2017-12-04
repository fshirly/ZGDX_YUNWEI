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
		src="${pageContext.request.contextPath}/js/monitor/deviceWBListSetting/WBListView_edit.js"></script>
		<div class="easyui-tabs" data-options="tabPosition:'left',fit:false,plain:false" id="editWBSetting" style="overflow: auto;height:400px;"> 
		<input type="hidden" id="defineId" >
			<div id="divAlarmDefine" title="黑白名单定义" data-options="closable:false" style="overflow: auto">
				<table id="tblAlarmAdd"  class="tableinfo"  style="width: 600px;">
					<input id="selectedRow" type="hidden" value='${param.selectedRow}'/>
					<tbody>
					<tr>
						<td>
							<b class="title">设备IP：</b>
							<label id="deviceIP"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">类型：</b>
							<label id="portType">接口</label>
						</td>					
					</tr>
					<tr>
						<td>
							<b class="title">状态：</b>
							<input type="radio" name="operateStatusForDevice"  value="1">&nbsp;启用
							&nbsp;
							<input type="radio" name="operateStatusForDevice"  value="0">&nbsp;不启用
						</td>					
					</tr>							
					</tbody>
				</table>
				<div id="collectorListDiv">
					<lable>选择下发的采集机</lable>
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
