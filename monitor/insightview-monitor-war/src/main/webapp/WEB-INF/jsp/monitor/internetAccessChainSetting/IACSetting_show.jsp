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
		src="${pageContext.request.contextPath}/js/monitor/internetAccessChainSetting/IACSetting_show.js"></script>
			<div>
				<form>
				<input id="selectedRow" type="hidden" value='${param.selectedRow}'/>
				<table id="tblAlarmAdd" class="tableinfo">
					<tr>
						<td>
							<b class="title">设备IP：</b>
							<label id="deviceIP"></label>
						</td>
						<td>
							<b class="title">测试连接地址：</b>
							<label id="connIPAddresss"></label>
						</td>
					</tr>
					<tr>
						<td>
							<b class="title">是否启用：</b>
							<label id="operateStatus"></label>
						</td>
						<td>
							<b class="title">采集机：</b>
							<label id="collector"></label>
						</td>
					</tr>
				</table>
				</form>
				<div id="chainSettingListDiv" class="datas" >
					<label>链路名称与网关：</label>
					<table id="chainSettingList" >
					</table>
				</div>
			</div>
		<div class="conditionsBtn">
			<input type="button" id="btnSave" value="确定" onclick="javascript:confirm();"/>
			<input type="button" id="btnClose" value="取消" onclick="javascript:cancle();"/>
		</div>
		<div id='event_select_dlg' class='event_select_dlg'></div>
	</body>
</html>
