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
		src="${pageContext.request.contextPath}/js/monitor/alarmMgr/mokpithreshold/moKPIThreshold_detail.js"></script>
		<%
			String ruleID =(String)request.getAttribute("ruleID");
		 %>
	<div id="divThresholdDetail" style="overflow:hidden;">
		<input type="hidden" id="ruleID" value="<%= ruleID%>"/>
	  <div style="overflow:auto;width: 600px;height: 416px">
		<table id="tblThresholdDetail" class="tableinfo1">
			<tr>
				<td>
					<b class="title">对象类型：</b>
					<label id="lbl_className"></label>
				</td>
			</tr>
		 	<tr>
				<td>
					<b class="title">源对象：</b>
					<label id="lbl_sourceMOName"></label>
				</td>
			</tr>
			<tr id="isShow" style="display: none">
				<td>
					<b class="title">管理对象：</b>
					<label id="lbl_moName"/>
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">指标名称：</b>
					<label id="lbl_kpiName" />
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">上限阈值：</b>
					<label id="lbl_upThreshold" />
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">上限回归阈值：</b>
					<label id="lbl_upRecursiveThreshold" />
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">下限阈值：</b>
					<label id="lbl_downThreshold" class="x2" />
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">下限回归阈值：</b>
					<label id="lbl_downRecursiveThreshold" />
				</td>
			</tr>
			<tr>
				<td>
					<b class="title">单位：</b>
					<label id="lbl_descr" />
				</td>
			</tr>
		</table>
	  </div>
		<div class="conditionsBtn">
			<input type="button" id="btnClose" value="关闭" onclick="javascript:$('#popWin').window('close');"/>
		</div>
	</div>



		
	</body>
</html>
