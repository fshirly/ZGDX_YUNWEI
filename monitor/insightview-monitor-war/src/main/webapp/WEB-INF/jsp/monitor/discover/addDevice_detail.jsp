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
		<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/monitor/discover/addDevice_detail.js"></script>
		<%
			String taskId =(String)request.getAttribute("taskId");
			String flag =(String)request.getAttribute("flag");
		 %>
	<div id="divTaskDetail">
		<input type="hidden" id="taskId" value="<%= taskId%>"/>
		<input type="hidden" id="flag" value="<%= flag%>"/>
		<table id="tblTaskDetail" class="tableinfo">
			<tr>
				<td>
					<b class="title">任务ID：</b>
					<label id="lbl_taskid" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">IP地址：</b>
					<label id="lbl_ipaddress1" ></label>
				</td>
				<td>
					<b class="title">发现对象类型：</b>
					<label id="lbl_moClassLable" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">任务创建者：</b>
					<label id="lbl_creatorName" ></label>
				</td>
				<td>
					<b class="title">创建时间：</b>
					<label id="lbl_createtimeStr" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">执行任务采集机：</b>
					<label id="lbl_collectorName" ></label>
				</td>
				<td>
					<b class="title">下发发现任务的web应用ip：</b>
					<label id="lbl_webipaddress" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">更新周期：</b>
					<label id="lbl_updateinterval" ></label>
				</td>
				<td>
					<b class="title">发现深度：</b>
					<label id="lbl_hops" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">最近状态时间：</b>
					<label id="lbl_laststatustimeStr" ></label>
				</td>
				<td>
					<b class="title">操作进度：</b>
					<label id="lbl_progressStatusInfo" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">子网掩码：</b>
					<label id="lbl_netmask" ></label>
				</td>
				<td>
					<b class="title">最近操作结果：</b>
					<label id="lbl_lastopresult" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">错误信息：</b>
					<label id="lbl_errorinfo" class="x2"></label>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:closeWin()"/>
		</div>
	</div>
	</body>
</html>
