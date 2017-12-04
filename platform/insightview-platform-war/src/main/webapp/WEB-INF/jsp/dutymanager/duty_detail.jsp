<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看值班信息</title>
</head>
<body>
<style type="text/css">
.datagrid-sort-asc .datagrid-sort-icon{background: none; padding: 0}
</style>
	<div>
		<table class="tableinfo" style="width:670px;">
			<tr>
				<td><b class="title">带班领导：</b><label>${duty.Leader }</label><input type="hidden" name="id" value="${duty.ID }"/></td>
				<td><b class="title">值班日期：</b><label>${duty.DutyDate }</label></td>
			</tr>
			<tr>
				<td colspan="2"><b class="title">备勤人员：</b><label>${duty.reserveNames }</label></td>
			</tr>
			<tr>
				<td><b class="title">备勤等级：</b><label>${Level }</label></td>
			</tr>
		</table>		
		<div style="width: 670px; height: 180px;"><table id="orders"></table></div>
	</div>
	<div class="conditionsBtn">
		<a onclick="javascript:f('#popWin').window('close');">关闭</a>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dutymanager/duty_detail.js"></script>
</body>
</html>