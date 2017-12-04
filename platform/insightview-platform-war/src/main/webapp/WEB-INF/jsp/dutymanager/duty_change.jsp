<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调班</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/plugin/select2/select2.css"/>
	<div class="winbox">
	<h2>值班信息</h2>
	<div class="conditions">
	<table id="table_dutychange">
		<tr>
			<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值班日期：</b></td>
			<td><input type="text" id="happen" name="happen" class="easyui-datebox "  validator="{'default':'*','type':'datebox'}"/><dfn>*</dfn></td>
			<td><b>带班领导：&nbsp;&nbsp;</b><span id="leader"></span></td>
		</tr>
		<tr>
			<td><b>值班人/带班领导：</b></td>
			<td><select id="fromUser" name="fromUser" validator="{'default':'*','type':'select2'}" onchange="initToUser();"></select><dfn>*</dfn></td>
			<td><b>调班给：&nbsp;&nbsp;</b><select id="toUser" name="toUser" validator="{'default':'*','type':'select2'}"></select><dfn>*</dfn></td>
			<td class="btntd"><a href="#" id="dutychange_submit">提交</a></td>
		</tr>
	</table>
	</div>
	
	<h2>调班记录</h2>
	<div class="data top2">
		<table id="tblDutyChange"></table>
	</div>
	</div>
	<div class="conditionsBtn">
		<a href="javascript:$('#popWin').window('close');">关闭</a>	
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/dutymanager/duty_change.js"></script>
</body>
</html>