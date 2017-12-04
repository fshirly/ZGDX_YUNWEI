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
		src="${pageContext.request.contextPath}/js/monitor/discover/dbNames_list.js"></script>
		<%
			String deviceip =(String)request.getAttribute("deviceip");
			String port =(String)request.getAttribute("port");
			String dbmstype =(String)request.getAttribute("dbmstype");
			String moClassId =(String)request.getAttribute("moClassId");
			String moid =(String)request.getAttribute("moid");
			String isForPerfSet =(String)request.getAttribute("isForPerfSet");
			String moAlias =(String)request.getAttribute("moAlias");
		 %>
	    <input type="hidden" id="deviceipForDb2" value="<%= deviceip%>"/>
        <input type="hidden" id="portForDb2" value="<%= port%>"/>
        <input type="hidden" id="dbmstypeForDb2" value="<%= dbmstype%>"/>
        <input type="hidden" id="moClassIdForDb2" value="<%= moClassId%>"/>
        <input type="hidden" id="moidForDb2" value="<%= moid%>"/>
        <input type="hidden" id="isForPerfSet" value="<%= isForPerfSet%>"/>
		<input type="hidden" id="moAlias" value="<%= moAlias%>"/>
		<div>
			<h2> 请选择数据库名</h2>
			
			<table class="formtable1">
			  <tr>
				<td class="title">数据库名：</td>
				<td><input class="easyui-combobox" id="selectDbName"/></td>
			  </tr>
			</table>
			
			<div class="conditionsBtn">
				<input type="button" id="btnSave" value="确定" onclick="javascript:doAction();"/>
				<input type="button" id="btnClose" value="取消" onclick="javascript:$('#popWin2').window('close');"/>
			</div>
		</div>
	</body>
</html>
