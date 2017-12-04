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
		src="${pageContext.request.contextPath}/js/monitor/internetAccessChainSetting/IACSetting_edit.js"></script>
			<div id = "infoEditDiv">
				<form>
				<input id="selectedRow" type="hidden" value='${param.selectedRow}'/>
				<table class="tableinfo">
					<tr>
						<td>
							<b class="title">设备IP：</b>
							<label id="deviceIP"></label>
						</td>
						<td>
							<b class="title">状态：</b>
							<input type="radio" name="operateStatusForDevice"  value="1">&nbsp;启用
							&nbsp;
							<input type="radio" name="operateStatusForDevice"  value="0">&nbsp;不启用
						</td>	
					</tr>
				</table>
				<table class="formtable">
					<tr>
						<td class="title" style="text-align: left;">测试连接地址：
						<input name="connIPAddresss" id="connIPAddresss" validator="{'default':'*','type':'domain'}"></input><b>*</b>
						</td>		
					</tr>
				</table>	
				</form>
				<div id="chainSettingListDiv" class="datas" >
					<lable>链路名称与网关:</lable>
					<table id="chainSettingList" >
					</table>
				</div>
				<div id="collectorListDiv" style="margin-top: 40px;" class="datas">
					<lable>选择下发的采集机(必选):</lable>
					<table id="collectorsList" >
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
