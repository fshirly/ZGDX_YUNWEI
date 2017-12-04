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
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/traptask/trapTask_detail.js"></script>
		<%
			String taskID =(String)request.getAttribute("taskID");
		 %>
	<div id="divTaskDetail">
		<input type="hidden" id="taskID" value="<%= taskID%>"/>
		<table id="tblTaskDetail" class="tableinfo">
			<tr>
				<td>
					<b class="title">任务ID：</b>
					<label id="lbl_taskID" ></label>
				</td>
				<td>
					<b class="title">trap服务器地址：</b>
					<label id="lbl_serverIP" ></label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<b class="title">trap过滤表达式：</b>
					<label id="lbl_filterExpression" class="x2"></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">任务创建者：</b>
					<label id="lbl_creatorName" ></label>
				</td>
				<td>
					<b class="title">创建时间：</b>
					<label id="lbl_createTime" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">任务执行采集机：</b>
					<label id="lbl_collectorName" ></label>
				</td>
				<td>
					<b class="title">trap事件：</b>
					<label id="lbl_alarmOID" ></label>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">操作进度：</b>
					<label id="lbl_progressStatus" ></label>
				</td>
				<td>
					<b class="title">最近操作结果：</b>
					<label id="lbl_lastOPResult" ></label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<b class="title">错误信息：</b>
					<label id="lbl_errorInfo" class="x2"></label>
				</td>
			</tr>
		</table>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>
	</body>
</html>
